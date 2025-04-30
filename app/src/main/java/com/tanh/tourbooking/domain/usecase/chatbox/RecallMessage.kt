package com.tanh.tourbooking.domain.usecase.chatbox

import com.google.firebase.Timestamp
import com.tanh.tourbooking.domain.repository.firestore.MessageRepository
import javax.inject.Inject

class RecallMessage @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(timestamp: Timestamp, chatId: String) {
        messageRepository.recallMessage(timestamp, chatId)
    }
}