package com.tanh.tourbooking.presentation.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.model.ChatBox
import com.tanh.tourbooking.domain.usecase.chatbox.ChatUseCaseManager
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.util.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatUseCaseManager: ChatUseCaseManager
) : ViewModel() {

    val userId = 1

    private val _chats = MutableStateFlow<List<ChatBox>>(emptyList())
    val chat = _chats.asStateFlow()

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            chatUseCaseManager.observeChatlist(userId).collect { res ->
                res.onSuccess {
                    _chats.value = it
                }
            }
        }
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }

    fun onNavToMessage(chatId: String) {
        sendEvent(OneTimeEvent.Navigate(Route.MESSAGE_SCREEN.toString() + "/$chatId"))
    }

}