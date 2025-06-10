package com.tanh.tourbooking.presentation.explore

import android.annotation.SuppressLint
import android.os.Build
import android.text.style.TabStopSpan.Standard
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.model.TourUnit
import com.tanh.tourbooking.domain.usecase.tour.FoundTourUseCase
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.util.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val foundTourUseCase: FoundTourUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ExploreUiState())
    val state = _state.asStateFlow()

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            startFilter()
        }
    }


    fun onEvent(event: ExploreEvent) {
        when(event) {
            ExploreEvent.StartFilter -> startFilter()
            is ExploreEvent.TypeDepartureDate -> typeDepartureDate(event.date)
            is ExploreEvent.TypePlace -> typePlace(event.place)
            is ExploreEvent.OnPriceRangeChange -> onPriceRangeChange(event.minPrice, event.maxPrice)
        }
    }

    @SuppressLint("NewApi")
    fun onNavToDetail(tour: TourUnit) {
        val jsonTour = Json.encodeToString(tour)
        val encodedJson = URLEncoder.encode(jsonTour, StandardCharsets.UTF_8)
        sendEvent(OneTimeEvent.Navigate(Route.DETAIL_SCREEN.toString() + "/$encodedJson"))
    }

    private fun onPriceRangeChange(minPrice: Double, maxPrice: Double) {
        _state.update {
            it.copy(
                startPrice = minPrice,
                endPrice = maxPrice
            )
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            startFilter()
        }
    }

    fun typePlace(query: String) {
        _state.update { state ->
            state.copy(place = query)
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            startFilter()
        }
    }

    private fun typeDepartureDate(date: String) {
        _state.update { state ->
            state.copy(
                startDate = date
            )
        }
        Log.d("EXP2", "Run")
        startFilter()
    }

    fun startFilter() {
        viewModelScope.launch {
            foundTourUseCase(
                destination = _state.value.place,
                departureDate = _state.value.startDate,
                price = formatPrice(_state.value.startPrice, _state.value.endPrice),
                page = 0
            ).apply {
                onSuccess {
                    _state.update { state ->
                        state.copy(
                            tourUnitList = it
                        )
                    }
                }
                onError {
                    showSnackbar(it.message ?: "Unknown error")
                }
            }
        }
    }

    fun showSnackbar(message: String) {
        sendEvent(OneTimeEvent.ShowSnackbar(message))
    }

    fun formatPrice(startPrice: Double, endPrice: Double): String {
        val formattedStartPrice = startPrice.toInt()
        val formattedEndPrice = endPrice.toInt()
        return "$formattedStartPrice-$formattedEndPrice"
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }

}