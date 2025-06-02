package com.tanh.tourbooking.domain.usecase.chatbox

import com.tanh.tourbooking.domain.repository.api.UserRepository
import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import com.tanh.tourbooking.domain.repository.firestore.NotificationHandler
import javax.inject.Inject

class AllowUserToChat @Inject constructor(
    private val repositoryImpl: UserRepository,
    private val chatRepository: ChatRepository,
    private val handler: NotificationHandler
) {

    //return chatBoxId
    suspend operator fun invoke(tourUnitId: String, userId: Int): String? {
        val isValid = repositoryImpl.checkBookingIdTour(tourUnitId)
        val chatId = if (isValid) chatRepository.joinChatBox(tourUnitId, userId) else null
        if (chatId != null) {
            handler.subscribeTopic(chatId)  //subscribe topic to receive messages
        }
        return chatId
    }

}