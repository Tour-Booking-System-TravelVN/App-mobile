package com.tanh.tourbooking.domain.usecase.chatbox

import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import javax.inject.Inject

class AcceptUserJoinChat @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(userId: Int, chatId: String) {
        repository.acceptUserIdToChat(userId, chatId)
    }
}