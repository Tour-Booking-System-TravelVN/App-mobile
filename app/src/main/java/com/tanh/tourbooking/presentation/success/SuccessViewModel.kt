package com.tanh.tourbooking.presentation.success

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.response.ConfirmPaymentResponse
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.usecase.payment.ConfirmPaymentUseCase
import com.tanh.tourbooking.domain.usecase.payment.GetBookingIdUseCase
import com.tanh.tourbooking.presentation.profile.ProfileUiState
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuccessViewModel @Inject constructor(
    private val confirmPaymentUseCase: ConfirmPaymentUseCase,
    private val getBookingIdUseCase: GetBookingIdUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(SuccessUiState())
    val state = _state.asStateFlow()

    private val _orderCode = mutableStateOf("")

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            _orderCode.value = savedStateHandle.get<String>("orderCode") ?: ""
            _state.update { state ->
                state.copy(isLoading = true)
            }
            delay(1000L)
            if(_orderCode.value.isBlank()) {
                _state.update { state ->
                    state.copy(isLoading = false, error = "Thanh toán thất bại")
                }
            }
            else {
                confirmPaymentUseCase(orderCode = _orderCode.value).apply {
                    onError {
                        _state.update { state ->
                            state.copy(isLoading = false, error = it.message)
                        }
                    }
                }
                getBookingIdUseCase(orderCode = _orderCode.value).apply {
                    onSuccess {
                        _state.update { state ->
                            state.copy(isLoading = false, bookingId = it)
                        }
                    }
                    onError {
                        _state.update { state ->
                            state.copy(isLoading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }

    fun navToRoute(route: String) {
        sendEvent(OneTimeEvent.Navigate(route))
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send((event))
        }
    }

}