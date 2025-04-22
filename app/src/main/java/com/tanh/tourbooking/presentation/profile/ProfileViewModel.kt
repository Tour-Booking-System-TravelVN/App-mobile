package com.tanh.tourbooking.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.model.Information
import com.tanh.tourbooking.domain.usecase.auth.CheckRoleUseCase
import com.tanh.tourbooking.domain.usecase.auth.GetInformationUseCase
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
class ProfileViewModel @Inject constructor(
    private val checkRoleUseCase: CheckRoleUseCase,
    private val getInformationUseCase: GetInformationUseCase
): ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            launch {
                getInformation()
            }
            launch {
                getRole()
            }
        }
    }

    private suspend fun getRole() {
        _state.update {
            it.copy(
                role = checkRoleUseCase()
            )
        }
    }

    private suspend fun getInformation() {
        _state.value = _state.value.copy(isLoading = true)
        getInformationUseCase().apply {
            onSuccess { information ->
                when (val infor = information.second) {
                    is Information.Customer -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                customer = infor
                            )
                        }
                    }

                    is Information.TourGuide -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                tourGuide = infor
                            )
                        }
                    }
                }
            }
            onError { exception ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = exception.message
                    )
                }
                showSnackBar(exception.message ?: "Unknown error")
            }
        }
    }


    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.OnNavToPrivateInformation -> onNavToPrivateInformation(event.route)
        }
    }

    private fun showSnackBar(message: String) {
        sendEvent(OneTimeEvent.ShowSnackbar(message))
    }

    private fun onNavToPrivateInformation(route: String) {
        sendEvent(OneTimeEvent.Navigate(route))
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send((event))
        }
    }

}