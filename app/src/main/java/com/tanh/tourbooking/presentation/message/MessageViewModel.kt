package com.tanh.tourbooking.presentation.message

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.usecase.chatbox.ChatUseCaseManager
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MessageViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val chatUseCaseManager: ChatUseCaseManager
): ViewModel() {

    val userId by mutableIntStateOf(1)
    var username by mutableStateOf("Tuấn Anh")

    private val _state = MutableStateFlow(MessageUiState())
    val state = _state.asStateFlow()

    var chatId by mutableStateOf("")

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        chatId = savedStateHandle.get<String>("chatId") ?: ""
        if(chatId.isNotBlank()) {
            viewModelScope.launch {
                launch {
                    chatUseCaseManager.observeMessage(chatId).collect { res ->
                        res.onSuccess { list ->
                            _state.update {
                                it.copy(
                                    messages = list,
                                    isLoading = false
                                )
                            }
                        }
                        res.onError {
                            _state.update {
                                it.copy(
                                    error = it.error,
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
                launch {
                    chatUseCaseManager.observeChat(chatId).collect { res ->
                        Log.d("MSG1", "Run")
                        res.onSuccess { chat ->
                            _state.update {
                                it.copy(
                                    chatbox = chat
                                )
                            }
                            Log.d("MSG1", "$chat")
                        }
                        res.onError {
                            _state.update {
                                it.copy(
                                    error = it.error,
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            }
        } else {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
        }
    }



    fun sendMessage(message: String) {
        viewModelScope.launch {
            chatUseCaseManager.createMessage(chatId, message, userId, username)
            chatUseCaseManager.notifyMessage(chatId, message, _state.value.chatbox?.name ?:  "No name")
        }
    }

    fun onPopBackStack() {
        sendEvent(OneTimeEvent.PopBackStack)
    }

    fun onNavigate(route: String) {
        sendEvent(OneTimeEvent.Navigate(route))
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }

}