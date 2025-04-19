package com.tanh.tourbooking.presentation.detail_tour

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.model.TourUnit
import com.tanh.tourbooking.domain.usecase.tour.GetRatingByTourUnitIdUseCase
import com.tanh.tourbooking.domain.usecase.tour.GetTourProgramByTourIdUseCase
import com.tanh.tourbooking.domain.usecase.tour.GetTourUnitCalendarUseCase
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.util.Calendar
import com.tanh.tourbooking.util.Month
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
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val tourRating: GetRatingByTourUnitIdUseCase,
    private val tourProgram: GetTourProgramByTourIdUseCase,
    private val tourCalendar: GetTourUnitCalendarUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    private val _calendar = MutableStateFlow(CalendarUiState())
    val calendar = _calendar.asStateFlow()

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

    fun onEvent(event: DetailEvent) {
        when (event) {
            DetailEvent.BookTour -> Unit
            DetailEvent.OnLoadCalendar -> onLoadCalendar()
        }
    }

    private fun onLoadCalendar() {
        viewModelScope.launch {
            val tourId = _state.value.tourUnit?.tour?.tourId
            if (tourId != null) {
                //goi api
                _calendar.update {
                    it.copy(isLoading = true)
                }
                tourCalendar(tourId).apply {
                    onSuccess { list ->
                        Log.d("CAL2", list.toString())
                        _calendar.update {
                            it.copy(
                                isLoading = false,
                                calendar = list
                            )
                        }
                    }
                    onError { exception ->
                        _calendar.update {
                            it.copy(
                                isLoading = false,
                                error = exception.message
                            )
                        }
                    }
                }

                val currentMonth = LocalDate.now().monthValue
                val currentYear = LocalDate.now().year

                val months1: MutableList<Pair<Int, Month>> = mutableListOf()

                val mo = Calendar.generateYearData(startMonth = currentMonth, year = currentYear)
                Log.d("CAL", mo.toString())

                (currentMonth..12).forEach { monthvalue ->
                    val index = monthvalue - currentMonth
                    val firstDayOfWeek = Calendar.getFirstDaysOfWeek(currentYear, monthvalue)

                    if (index in mo.indices) {
                        val data = mo[index]

                        val updatedDays = data.days.map { day ->
                            val matched = _calendar.value.calendar.any { (_, tourUnit) ->
                                val departureDate = tourUnit.departureDate
                                departureDate.year == currentYear &&
                                        departureDate.monthValue == monthvalue &&
                                        departureDate.dayOfMonth == day.date
                            }
                            day.copy(data = matched)
                        }
                        months1.add(firstDayOfWeek to data.copy(days = updatedDays))
                    }
                }
                _calendar.update {
                    it.copy(
                        months = months1
                    )
                }
            }
        }
    }

    private fun updateCalendarWithTourData() {
        val months = _calendar.value.months
        val calendar = _calendar.value.calendar

        val updatedMonths = months.map { (firstDayOfWeek, month) ->
            val updatedDays = month.days.map { day ->
                val matched = calendar.any { (_, tourUnit) ->
                    val departureDate = tourUnit.departureDate
                    departureDate.year == month.year &&
                            departureDate.monthValue == month.month &&
                            departureDate.dayOfMonth == day.date
                }
                day.copy(data = matched)
            }
            firstDayOfWeek to month.copy(days = updatedDays)
        }
        _calendar.update {
            it.copy(months = updatedMonths)
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
                onError { error ->
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
                onError { error ->
                    _state.update {
                        it.copy(
                            ratingError = error.message
                        )
                    }
                }
            }
        }
    }


    private fun getTour() {
        val jsonTour = savedStateHandle.get<String>("jsonTour") ?: ""
        _state.update {
            it.copy(isLoading = true)
        }
        if (jsonTour.isNotBlank()) {

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