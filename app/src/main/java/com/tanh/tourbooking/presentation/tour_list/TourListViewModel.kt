package com.tanh.tourbooking.presentation.tour_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.usecase.tour.FoundTourUseCase
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.util.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class TourListViewModel @Inject constructor(
    private val foundTourUseCase: FoundTourUseCase,
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

    fun onEvent(event: TourListEvent) {
        when(event) {
            is TourListEvent.OnClickTour -> onNavToDetailTour(event.id)
        }
    }

    private fun onNavToDetailTour(id: String) {
        val foundTour = _state.value.list.first { it.tourUnitId == id }
        val jsonTour = Json.encodeToString(foundTour)
        val encodedJson = URLEncoder.encode(jsonTour, StandardCharsets.UTF_8.toString())
        val route = Route.DETAIL_SCREEN.toString() + "/${encodedJson}"
        sendEvent(OneTimeEvent.Navigate(route))
    }

    private suspend fun getToursInit() {
        _state.update {
            it.copy(isLoading = true)
        }
        foundTourUseCase(
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