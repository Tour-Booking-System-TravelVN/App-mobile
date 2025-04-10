package com.tanh.tourbooking.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.usecase.auth.RegisterUseCase
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
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnNavToLogin -> onNavToLogin(event.route)
            is RegisterEvent.RegisterAccount -> onRegisterAccount()
        }
    }

    private fun onRegisterAccount() {
        if (checkValidInput()) {
            viewModelScope.launch {
                _state.value = _state.value.copy(isLoading = true)
                registerUseCase(
                    username = _state.value.input.username,
                    password = _state.value.input.password,
                    email = _state.value.input.email
                ).apply {
                    onSuccess {
                        sendEvent(OneTimeEvent.ShowSnackbar("Register successfully"))
                        _state.update { state ->
                            state.copy(
                                isRegisterSuccess = true,
                                isLoading = false
                            )
                        }
                    }
                    onError {
                        sendEvent(OneTimeEvent.ShowSnackbar(it.toString()))
                        _state.update { state ->
                            state.copy(
                                isRegisterSuccess = false,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onNavToLogin(route: String) {
        sendEvent(OneTimeEvent.Navigate(route))
    }

    private fun checkValidInput(): Boolean {
        if (_state.value.input.username.isBlank()
            || _state.value.input.password.isBlank()
            || _state.value.input.confirmPassword.isBlank()
            || _state.value.input.email.isBlank()
        ) {
            sendEvent(OneTimeEvent.ShowSnackbar("Please fill all input"))
            return false
        }
        if (_state.value.input.password != _state.value.input.confirmPassword) {
            sendEvent(OneTimeEvent.ShowSnackbar("Password not match"))
            return false
        }
        return true
    }

    fun onUsernameChange(username: String) {
        _state.update { state ->
            state.copy(
                input = state.input.copy(
                    username = username
                )
            )
        }
    }

    fun onPassword(password: String) {
        _state.update { state ->
            state.copy(
                input = state.input.copy(
                    password = password
                )
            )
        }
    }

    fun onConfirmPassword(confirmPassword: String) {
        _state.update { state ->
            state.copy(
                input = state.input.copy(
                    confirmPassword = confirmPassword
                )
            )
        }
    }

    fun onEmailChange(email: String) {
        _state.update { state ->
            state.copy(
                input = state.input.copy(
                    email = email
                )
            )
        }
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }

}