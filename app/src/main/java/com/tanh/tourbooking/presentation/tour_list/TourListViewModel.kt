package com.tanh.tourbooking.presentation.tour_list

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.usecase.auth.GetToursByPlaceUseCase
import com.tanh.tourbooking.presentation.home.HomeUiState
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
class TourListViewModel @Inject constructor(
    private val getToursByPlaceUseCase: GetToursByPlaceUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var _place = mutableStateOf("")

    private val _state = MutableStateFlow(TourListUiState())
    val state = _state.asStateFlow()

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            _place.value = savedStateHandle.get<String>("place") ?: ""
            if(_place.value.isNotBlank()) {
                getToursInit()
            }
        }
    }

    private suspend fun getToursInit() {
        _state.update {
            it.copy(isLoading = true)
        }
        getToursByPlaceUseCase(
            destination = _place.value,
            price = "0-infinity",
            departureDate = null,
            page = null
        ).apply {
            onSuccess {
                _state.update { st ->
                    st.copy(
                        isLoading = false,
                        list = it
                    )
                }
            }
            onError {
                _state.update { st ->
                    st.copy(
                        isLoading = false,
                        error = it.message
                    )
                }
                showSnackBar(it.message ?: "Unknown error")
            }
        }
    }

    private fun showSnackBar(message: String) {
        sendEvent(OneTimeEvent.ShowSnackbar(message))
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send((event))
        }
    }
}