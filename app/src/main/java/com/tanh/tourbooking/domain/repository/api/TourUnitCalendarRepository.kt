package com.tanh.tourbooking.domain.repository.api

import com.tanh.tourbooking.data.model.response.AvailableMonthResponse
import com.tanh.tourbooking.data.model.response.TourUnitCalendarResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.domain.model.TourUnitCalendar

interface TourUnitCalendarRepository {
    suspend fun getAvailableMonth(tourId: String): Result<AvailableMonthResponse, NetworkError>
    suspend fun getTourUnitCalendar(month: Int, year: Int, tourId: String): Result<TourUnitCalendarResponse, NetworkError>
}