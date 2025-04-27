package com.tanh.tourbooking.domain.usecase.chatbox

import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import javax.inject.Inject

class ObserveWaitingId @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(chatId: String): Resources<List<Int>, Exception> {
        return repository.observeWaitingId(chatId)
    }
}