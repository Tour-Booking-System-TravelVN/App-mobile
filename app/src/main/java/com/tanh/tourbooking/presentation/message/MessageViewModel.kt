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
import com.tanh.tourbooking.data.mappers.toMessageDto
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.model.Information
import com.tanh.tourbooking.domain.model.Message
import com.tanh.tourbooking.domain.usecase.auth.GetInformationUseCase
import com.tanh.tourbooking.domain.usecase.chatbox.ChatUseCaseManager
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.util.Role
import com.tanh.tourbooking.util.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val chatUseCaseManager: ChatUseCaseManager,
    private val getInformationUseCase: GetInformationUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MessageUiState())
    val state = _state.asStateFlow()

    var chatId by mutableStateOf("")

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        chatId = savedStateHandle.get<String>("chatId") ?: ""
        Log.d("MSG5", chatId)
        if (chatId.isNotBlank()) {
            viewModelScope.launch {
                launch {
                    getInformationUseCase().onSuccess {
                        when (val role = it.second) {
                            is Information.Customer -> {
                                Log.d("CAT1", role.toString())
                                _state.update { state ->
                                    state.copy(
                                        customer = role,
                                        role = Role.CUSTOMER
                                    )
                                }
                            }

                            is Information.TourGuide -> {
                                Log.d("CAT1", role.toString())
                                _state.update { state ->
                                    state.copy(
                                        tourguide = role,
                                        role = Role.TOURGUIDE
                                    )
                                }
                            }
                        }
                    }
                }
                launch {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                    chatUseCaseManager.observeMessage(chatId).collect { res ->
                        res.onSuccess { list ->
                            Log.d("MSG1", list.toString())
                            _state.update {
                                it.copy(
                                    messages = list,
                                    isLoading = false
                                )
                            }
                        }
                        res.onError {
                            Log.d("MSG1", it.message.toString())
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

    fun onNavToWaitingId() {
        sendEvent(OneTimeEvent.Navigate(Route.WAITING_SCREEN.toString() + "/$chatId"))
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            val userId = _state.value.customer?.id ?: _state.value.tourguide?.id ?: 0
            val username = _state.value.customer?.lastname ?: _state.value.tourguide?.lastname ?: "Anonymous"
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

    fun recallMessage(currentMessage: Message) {
        viewModelScope.launch {
            chatUseCaseManager.recallMessage(
                timestamp = currentMessage.toMessageDto().timestamp,
                chatId = chatId
            )
        }
    }

}

val MessageUiState.userId: Int?
    get() = when(role) {
        Role.CUSTOMER -> customer?.id
        Role.TOURGUIDE -> tourguide?.id
        Role.NULL -> null
    }