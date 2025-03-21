package com.tanh.tourbooking.domain.usecase.chatbox

import android.os.Build
import androidx.annotation.RequiresApi
import com.tanh.tourbooking.data.model.mappers.toChatBox
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.domain.model.ChatBox
import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveChatlist @Inject constructor(
    private val chatRepository: ChatRepository
) {

    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(userId: Int): Flow<Resources<List<ChatBox>, Exception>> {
        return chatRepository.observeChatboxList(userId).map { res ->
            when (res) {
                is Resources.Error -> Resources.Error(res.error)
                is Resources.Success -> Resources.Success(res.data.map { it.toChatBox() })
            }
        }
    }

}