package com.tanh.tourbooking.data.repository.api

import com.tanh.tourbooking.data.model.response.AvailableMonthResponse
import com.tanh.tourbooking.data.model.response.TourUnitCalendarResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.networking.api.TourBookingApi
import com.tanh.tourbooking.data.networking.util.safeCall
import com.tanh.tourbooking.di.IODispatcher
import com.tanh.tourbooking.domain.model.TourUnitCalendar
import com.tanh.tourbooking.domain.repository.api.TourUnitCalendarRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TourUnitCalendarRepositoryImpl @Inject constructor(
    private val api: TourBookingApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher
): TourUnitCalendarRepository {

    override suspend fun getAvailableMonth(tourId: String): Result<AvailableMonthResponse, NetworkError> {
        return withContext(dispatcher) {
            safeCall {
                api.getAvailableMonthByTourId(tourId)
            }
        }
    }

    override suspend fun getTourUnitCalendar(
        month: Int,
        year: Int,
        tourId: String
    ): Result<TourUnitCalendarResponse, NetworkError> {
        return withContext(dispatcher) {
            safeCall {
                api.getTourUnitCalendar(
                    month,
                    year,
                    tourId
                )
            }
        }
    }
}