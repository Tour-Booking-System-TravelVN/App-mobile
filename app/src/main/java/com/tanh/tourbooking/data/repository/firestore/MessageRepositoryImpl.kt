package com.tanh.tourbooking.data.repository.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.tanh.tourbooking.data.model.dto.MessageDto
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.di.IODispatcher
import com.tanh.tourbooking.domain.repository.firestore.MessageRepository
import com.tanh.tourbooking.util.Collections
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    @IODispatcher private val dispatcherIO: CoroutineDispatcher
) : MessageRepository {

    private val chatBoxCollection = firestore.collection(Collections.CHATBOX)

    //document/collection/documentId/collection/documentId

    override fun observeMessages(chatId: String): Flow<Resources<List<MessageDto>, Exception>> {
        return callbackFlow {
            var listener: ListenerRegistration? = null
            try {
                listener = chatBoxCollection.document(chatId)
                    .collection(Collections.MSGLST)
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            trySend(Resources.Error(error))
                            return@addSnapshotListener
                        }
                        val result = if (snapshot != null) {
                            Resources.Success(
                                snapshot.toObjects(MessageDto::class.java).mapNotNull { it })
                        } else {
                            Resources.Error(Exception("Not found documents"))
                        }
                        trySend(result).isSuccess
                    }
            } catch (e: Exception) {
                Resources.Error(e)
            }
            awaitClose {
                listener?.remove()
            }
        }.flowOn(dispatcherIO)
    }

    override suspend fun sendMessage(chatId: String, message: MessageDto) {
        withContext(dispatcherIO) {
            val messageMap = hashMapOf(
                "senderId" to message.senderId,
                "text" to message.text,
                "timestamp" to message.timestamp,
                "senderName" to message.senderName
            )

            chatBoxCollection.document(chatId)
                .collection(Collections.MSGLST)
                .add(messageMap)
                .await()

            val updatedLastMessageAndTime = mapOf(
                "message" to message.text,
                "lastTimestamp" to message.timestamp
            )
            chatBoxCollection.document(chatId)
                .update(updatedLastMessageAndTime)
                .await()

        }
    }

}