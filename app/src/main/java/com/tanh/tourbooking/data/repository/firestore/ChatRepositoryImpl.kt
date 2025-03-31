package com.tanh.tourbooking.data.repository.firestore

import android.os.Build
import android.util.Log
import androidx.compose.animation.core.snap
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.messaging.FirebaseMessaging
import com.tanh.tourbooking.data.model.dto.ChatBoxDto
import com.tanh.tourbooking.data.model.mappers.TimeFormatter
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.di.IODispatcher
import com.tanh.tourbooking.domain.model.ChatBox
import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import com.tanh.tourbooking.util.Collections
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.ZoneId
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseMessaging: FirebaseMessaging,
    @IODispatcher private val dispatcherIO: CoroutineDispatcher
) : ChatRepository {

    private val chatBoxCollection = firestore.collection(Collections.CHATBOX)

    override fun observeChatboxList(userId: Int): Flow<Resources<List<ChatBoxDto>, Exception>> {
        return callbackFlow {
            var listener: ListenerRegistration? = null
            try {
                listener = chatBoxCollection.whereArrayContains("participants", userId)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            trySend(Resources.Error(error))
                        } else {
                            val result = if (snapshot != null) {
                                Resources.Success(
                                    snapshot.toObjects(ChatBoxDto::class.java).mapNotNull { it })
                            } else {
                                Resources.Error(Exception("Not found documents"))
                            }
                            trySend(result).isSuccess
                        }
                    }
            } catch (e: Exception) {
                trySend(Resources.Error(e))
            }
            awaitClose {
                listener?.remove()
            }
        }.flowOn(dispatcherIO)
    }

    override suspend fun createChatBox(chatBox: ChatBox) {
        withContext(dispatcherIO) {
            chatBoxCollection.add(chatBox)
        }
    }

    override suspend fun joinChatBox(uniqueBookingId: Int, userId: Int): String? {
        return withContext(dispatcherIO) {
            val snapshot = chatBoxCollection.whereEqualTo("uniqueBookingId", uniqueBookingId)
                .get().await()
            if (snapshot.isEmpty) return@withContext null

            val documentId = snapshot.documents[0].id

            return@withContext try {
                chatBoxCollection.document(documentId)
                    .update("participants", FieldValue.arrayUnion(userId))
                    .await()
                documentId
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun observeChatBox(chatId: String): Flow<Resources<ChatBoxDto, Exception>> {
        return callbackFlow {
            var listener: ListenerRegistration? = null
            try {
                listener = chatBoxCollection.document(chatId)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            trySend(Resources.Error(error))
                        } else {
                            val result = if (snapshot != null) {
                                Resources.Success(snapshot.toObject(ChatBoxDto::class.java)!!)
                            } else {
                                Resources.Error(Exception("Not found documents"))
                            }
                            trySend(result).isSuccess
                        }
                    }
            } catch (e: Exception) {
                trySend(Resources.Error(e))
            }
            awaitClose {
                listener?.remove()
            }
        }.flowOn(dispatcherIO)
    }

    //delete chatbox if time gap is more than 7 days
    override suspend fun deleteInactiveChatBox(chatId: String) {
        withContext(dispatcherIO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                chatBoxCollection.document(chatId).get().await()?.let { result ->
                    result.toObject(ChatBoxDto::class.java)?.also { chatBox ->
                        val lastTimestamp = chatBox.lastTimestamp.toDate().toInstant() //tg cuoi cung
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        val currentTimeStamp = Timestamp.now().toDate().toInstant()  //tg hien tai
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        val timeDifference =
                            currentTimeStamp.toEpochDay() - lastTimestamp.toEpochDay()
                        if (timeDifference >= 7) {
                            firebaseMessaging.unsubscribeFromTopic(chatId).addOnCompleteListener { task ->
                                if(task.isSuccessful) {
                                    Log.d("FCM", "Delete successfully")
                                } else {
                                    Log.d("FCM", "Error deleting chatbox")
                                }
                            }
                            chatBoxCollection.document(chatId).delete().await()
                        }
                    }
                }
            }
        }
    }

}