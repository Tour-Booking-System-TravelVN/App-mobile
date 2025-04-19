package com.tanh.tourbooking.domain.usecase.tour

import android.util.Log
import com.tanh.tourbooking.data.mappers.toAvailableMonth
import com.tanh.tourbooking.data.mappers.toTourUnitCalendar
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.model.util.exception.toMessage
import com.tanh.tourbooking.domain.model.TourUnitCalendar
import com.tanh.tourbooking.domain.repository.api.TourUnitCalendarRepository
import io.opencensus.resource.Resource
import java.time.LocalDate
import javax.inject.Inject

class GetTourUnitCalendarUseCase @Inject constructor(
    private val repository: TourUnitCalendarRepository
) {
    suspend operator fun invoke(tourId: String): Resources<List<Pair<Int, TourUnitCalendar>>, Exception> {
        val availableMonths = repository.getAvailableMonth(tourId).let { result ->
            when (result) {
                is Result.Error -> return Resources.Error(Exception(result.error.toMessage()))
                is Result.Success -> result.data.result.map { it.toAvailableMonth() }
            }
        }

        if (availableMonths.isEmpty()) {
            return Resources.Success(emptyList())
        }

        val currentYear = LocalDate.now().year
        val resultList = mutableListOf<Pair<Int, TourUnitCalendar>>()

        try {
            for ((month, year) in availableMonths) {
                if (year == currentYear) {
//                    Log.d("CAL4", "Run1")
                    when (val result1 = repository.getTourUnitCalendar(month, year, tourId)) {
                        is Result.Error -> {
//                            Log.d("CAL4", "Run2")
                            Resources.Error(Exception(result1.error.toMessage()))
                        }
                        is Result.Success -> {
//                            Log.d("CAL4", "Run3: ${result1.data.result}")
                            val calendars = result1.data.result.map { it.toTourUnitCalendar() }
//                            Log.d("CAL4", "Run4 $calendars")
                            calendars.forEach { calendar ->
                                resultList.add(month to calendar)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Resources.Error(e)
        }

        return Resources.Success(resultList)
    }

}