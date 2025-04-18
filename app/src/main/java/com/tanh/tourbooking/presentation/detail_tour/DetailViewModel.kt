package com.tanh.tourbooking.presentation.detail_tour

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.model.TourUnit
import com.tanh.tourbooking.domain.usecase.tour.GetRatingByTourUnitIdUseCase
import com.tanh.tourbooking.domain.usecase.tour.GetTourProgramByTourIdUseCase
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val tourRating: GetRatingByTourUnitIdUseCase,
    private val tourProgram: GetTourProgramByTourIdUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            getTour()
            //rating
            launch {
                getRating()
            }
            launch {
                getTourProgram()
            }

        }
    }

    private suspend fun getTourProgram() {
        _state.value.tourUnit?.apply {
            val tourId = this.tour.tourId
            tourProgram(tourId = tourId).apply {
                onSuccess { list ->
                    _state.update {
                        it.copy(
                            tourProgram = list
                        )
                    }
                }
                onError {  error ->
                    _state.update {
                        it.copy(
                            tourProgramError = error.message
                        )
                    }
                }
            }
        }
    }

    private suspend fun getRating() {
        _state.value.tourUnit?.apply {
            val tourId = this.tour.tourId
            tourRating(tourId = tourId).apply {
                onSuccess { ratings ->
                    _state.update {
                        it.copy(
                            ratings = ratings
                        )
                    }
                }
                onError {  error ->
                    _state.update {
                        it.copy(
                            ratingError = error.message
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: DetailEvent) {
        when(event) {
            DetailEvent.BookTour -> Unit
        }
    }

    private fun getTour() {
        val jsonTour = savedStateHandle.get<String>("jsonTour") ?: ""
        _state.update {
            it.copy(isLoading = true)
        }
        if(jsonTour.isNotBlank()) {

            var cleanJsonTour = jsonTour.replace(Regex("%(?![0-9a-fA-F]{2})"), "%25")
            cleanJsonTour = cleanJsonTour.replace("\\+", "%2B")
            cleanJsonTour = cleanJsonTour.replace(Regex("%[0-9a-fA-F](?![0-9a-fA-F])"), "%25")
            val decodedJson = URLDecoder.decode(cleanJsonTour, StandardCharsets.UTF_8.toString())

            Json.decodeFromString<TourUnit>(decodedJson).apply {
                _state.update {
                    it.copy(
                        isLoading = false,
                        tourUnit = this
                    )
                }
            }
        } else {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = "Unknown error"
                )
            }
        }
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }

    fun popBackStack() {
        sendEvent(OneTimeEvent.PopBackStack)
    }

}