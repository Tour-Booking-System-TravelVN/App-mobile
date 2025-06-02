package com.tanh.tourbooking.domain.usecase.chatbox

import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import javax.inject.Inject

class GetChatBoxIdByTourUnitId @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(bookingId: String): String? {
        return chatRepository.getChatBoxIdByTourUnitid(bookingId)
    }
}