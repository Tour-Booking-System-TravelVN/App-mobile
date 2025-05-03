package com.tanh.tourbooking.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.model.Information
import com.tanh.tourbooking.domain.usecase.auth.GetInformationUseCase
import com.tanh.tourbooking.presentation.profile.ProfileUiState
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
class HomeViewModel @Inject constructor(
    private val getInformationUseCase: GetInformationUseCase
): ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            getInformationUseCase().onSuccess {result ->
                when(val infor = result.second) {
                    is Information.Customer -> {
                        _state.update { state ->
                            state.copy(
                                information = infor
                            )
                        }
                    }
                    is Information.TourGuide -> Unit
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.OnNavToTours -> onNavToToursScreen(event.route)
        }
    }

    fun onNavToCategoryScreen(categoryName: String) {
        sendEvent(OneTimeEvent.Navigate(Route.CATEGORY_SCREEN.toString() + "/$categoryName"))
    }

    private fun onNavToToursScreen(route: String) {
        sendEvent(OneTimeEvent.Navigate(route))
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send((event))
        }
    }

}