package com.tanh.tourbooking.domain.repository.api

import com.tanh.tourbooking.domain.model.FakCustomer
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): Flow<List<FakCustomer>>
    suspend fun checkBookingIdTour(bookingId: Int): Boolean
    fun getUserId(): Int?
}