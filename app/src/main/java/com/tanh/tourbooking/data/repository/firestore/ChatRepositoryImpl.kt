package com.tanh.tourbooking.data.repository.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.tanh.tourbooking.data.model.dto.ChatBoxDto
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    @IODispatcher private val dispatcherIO: CoroutineDispatcher
) : ChatRepository {

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
                                Resources.Success(snapshot.toObjects(ChatBoxDto::class.java).mapNotNull { it })
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

    private val chatBoxCollection = firestore.collection(Collections.CHATBOX)

    override suspend fun createChatBox(chatBox: ChatBox) {
        withContext(dispatcherIO) {
            chatBoxCollection.add(chatBox)
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

    override suspend fun deleteChatBox(chatId: String) {
        withContext(dispatcherIO) {
            chatBoxCollection.document(chatId).delete()
        }
    }
}