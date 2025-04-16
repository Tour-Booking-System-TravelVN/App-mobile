package com.tanh.tourbooking.domain.usecase.chatbox

import com.google.firebase.Timestamp
import com.tanh.tourbooking.data.model.dto.tour.MessageDto
import com.tanh.tourbooking.domain.repository.firestore.MessageRepository
import javax.inject.Inject

class CreateMessage @Inject constructor(
    private val repository: MessageRepository
) {

    suspend operator fun invoke(chatId: String, message: String, userId: Int, username: String) {

        val messageDto = MessageDto(
            senderId = userId,
            text = message,
            timestamp = Timestamp.now(),
            senderName = username
        )

        repository.sendMessage(
            chatId = chatId,
            message = messageDto
        )
    }

}