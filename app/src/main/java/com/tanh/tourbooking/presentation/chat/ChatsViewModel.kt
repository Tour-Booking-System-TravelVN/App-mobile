package com.tanh.tourbooking.presentation.chat

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.model.ChatBox
import com.tanh.tourbooking.domain.model.Information
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
class ChatsViewModel @Inject constructor(
    private val chatUseCaseManager: ChatUseCaseManager,
    private val getInformationUseCase: GetInformationUseCase
) : ViewModel() {

    private val _chats = MutableStateFlow<List<ChatBox>>(emptyList())
    val chat = _chats.asStateFlow()

    private val _waitedChats = MutableStateFlow<List<ChatBox>>(emptyList())
    val waitedChats = _waitedChats.asStateFlow()

    private val _state = MutableStateFlow(ChatsUiState())
    val state = _state.asStateFlow()

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            getInformationUseCase().onSuccess {
                when(val role = it.second) {
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
            launch {
                chatUseCaseManager.observeChatlist(_state.value.userId ?: 0).collect { res ->
                    res.onSuccess {
                        _chats.value = it
                    }
                }
            }
            launch {
                chatUseCaseManager.observeWaitingChat(_state.value.userId ?: 0).collect { res ->
                    res.onSuccess {
                        _waitedChats.value = it
                    }
                }
            }
        }
    }

    fun validChatBookingId(chatBookingId: String) {
        viewModelScope.launch {
            chatUseCaseManager.allowUserToChat(chatBookingId, _state.value.userId ?: 0).let { chatBoxId ->
                if(chatBoxId == null) {
                    sendEvent(OneTimeEvent.ShowSnackbar("Không tìm thấy id"))
                } else {
                    sendEvent(OneTimeEvent.ShowSnackbar("Vui lòng chờ hướng dẫn viên duyệt"))
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

val ChatsUiState.userId: Int?
    get() = when(role) {
        Role.CUSTOMER -> customer?.id
        Role.TOURGUIDE -> tourguide?.id
        Role.NULL -> null
    }