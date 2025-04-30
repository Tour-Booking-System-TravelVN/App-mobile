package com.tanh.tourbooking.domain.usecase.chatbox

import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import javax.inject.Inject

class RefuseUserToChat @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(userId: Int, chatId: String) {
        chatRepository.refureUserToChat(userId, chatId)
    }
}