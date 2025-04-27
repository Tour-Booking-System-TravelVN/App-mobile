package com.tanh.tourbooking.domain.usecase.chatbox

import com.tanh.tourbooking.data.mappers.toChatBox
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.domain.model.ChatBox
import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveWaitingChat @Inject constructor(
    private val chatRepository: ChatRepository
) {

    operator fun invoke(userId: Int): Flow<Resources<List<ChatBox>, Exception>> {
        return chatRepository.observeWaitingChatBoxList(userId).map { resources ->
            when(resources) {
                is Resources.Error -> Resources.Error(resources.error)
                is Resources.Success -> Resources.Success(resources.data.map { it.toChatBox() })
            }
        }
    }

}