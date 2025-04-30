package com.tanh.tourbooking.presentation.message.waiting_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.usecase.chatbox.ChatUseCaseManager
import com.tanh.tourbooking.presentation.login.LoginState
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaitingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val chatUseCaseManager: ChatUseCaseManager
): ViewModel() {

    var chatId by mutableStateOf("")

    private val _state = MutableStateFlow(WaitingUiState())
    val state = _state.asStateFlow()

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            chatId = savedStateHandle.get<String>("chatId") ?: ""
            if(chatId.isNotBlank()) {
                chatUseCaseManager.observeWaitingId(chatId = chatId).onSuccess {
                    _state.update { state ->
                        state.copy(
                            waitingIds = it
                        )
                    }
                }
            }
            else {
                showSnackbar("Oops")
            }
        }
    }

    fun acceptUserId(userId: Int) {
        viewModelScope.launch {
            chatUseCaseManager.acceptUserJoinChat(
                userId = userId,
                chatId = chatId
            )
            chatUseCaseManager.observeWaitingId(chatId = chatId).onSuccess {
                _state.update { state ->
                    state.copy(
                        waitingIds = it
                    )
                }
            }
        }
    }

    fun refuseUserId(userId: Int) {
        viewModelScope.launch {
            chatUseCaseManager.refuseUserToChat(
                userId = userId,
                chatId = chatId
            )
            chatUseCaseManager.observeWaitingId(chatId = chatId).onSuccess {
                _state.update { state ->
                    state.copy(
                        waitingIds = it
                    )
                }
            }
        }
    }

    fun showSnackbar(message: String) {
        sendEvent(OneTimeEvent.ShowSnackbar(message))
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }

}