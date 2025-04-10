package com.tanh.tourbooking.domain.usecase.chatbox

import com.google.firebase.Timestamp
import com.tanh.tourbooking.data.model.dto.MessageDto
import com.tanh.tourbooking.data.model.mappers.toMessageDto
import com.tanh.tourbooking.domain.model.Message
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