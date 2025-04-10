package com.tanh.tourbooking.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.usecase.auth.LoginUseCase
import com.tanh.tourbooking.presentation.util.OneTimeEvent
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.Login -> login(event.username, event.password)
            is LoginEvent.NavToRegister -> onNavToRegister(event.route)
        }
    }

    private fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            loginUseCase(username, password).apply {
                onSuccess {
                    Log.d("LOG1", it.toString())
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            isLoginSuccess = true
                        )
                    }
                }
                onError {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = it.errorMessage
                        )
                    }
                }
            }
            if(_state.value.isLoginSuccess) {
                showSnackbar("Login successfully")
                onNavToHome()
            } else {
                showSnackbar(_state.value.errorMessage ?: "Login failed")
            }
        }
    }

    private fun onNavToHome() {
        sendEvent(OneTimeEvent.Navigate(Route.HOME_SCREEN.toString()))
    }

    private fun onNavToRegister(route: String) {
        sendEvent(OneTimeEvent.Navigate(route))
    }

    private fun showSnackbar(message: String) {
        sendEvent(OneTimeEvent.ShowSnackbar(message))
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }

}