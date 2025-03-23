package com.tanh.tourbooking.domain.usecase.chatbox

import com.tanh.tourbooking.domain.repository.api.UserRepository
import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import javax.inject.Inject

class AllowUserToChat @Inject constructor(
    private val repositoryImpl: UserRepository,
    private val chatRepository: ChatRepository
) {

    //return chatBoxId
    suspend operator fun invoke(bookingIdTour: Int, userId: Int): String? {
        val isValid = repositoryImpl.checkBookingIdTour(bookingIdTour)
        return if (isValid) chatRepository.joinChatBox(bookingIdTour, userId) else null
    }

}