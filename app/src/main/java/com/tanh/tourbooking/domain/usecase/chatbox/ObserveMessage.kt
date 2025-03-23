package com.tanh.tourbooking.domain.usecase.chatbox

import android.os.Build
import androidx.annotation.RequiresApi
import com.tanh.tourbooking.data.model.mappers.toMessage
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.domain.model.Message
import com.tanh.tourbooking.domain.repository.firestore.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveMessage @Inject constructor(
    private val repository: MessageRepository
) {

    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(chatId: String): Flow<Resources<List<Message>, Exception>> {
        return repository.observeMessages(chatId).map { res ->
            when(res) {
                is Resources.Error -> Resources.Error(res.error)
                is Resources.Success -> Resources.Success(res.data.map { it.toMessage() })
            }
        }
    }

}