package com.tanh.tourbooking.domain.repository.api

import com.tanh.tourbooking.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): Flow<List<Customer>>
}