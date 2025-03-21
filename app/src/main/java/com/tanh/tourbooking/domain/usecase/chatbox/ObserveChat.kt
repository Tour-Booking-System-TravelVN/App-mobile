package com.tanh.tourbooking.domain.usecase.chatbox

import android.os.Build
import androidx.annotation.RequiresApi
import com.tanh.tourbooking.data.model.mappers.toChatBox
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.model.ChatBox
import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveChat @Inject constructor(
    private val chatRepository: ChatRepository
) {

    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(matchId: String): Flow<Resources<ChatBox, Exception>> {
        return chatRepository.observeChatBox(matchId).map {
            when(it) {
                is Resources.Error -> Resources.Error(it.error)
                is Resources.Success -> Resources.Success(it.data.toChatBox())
            }
        }
    }

}