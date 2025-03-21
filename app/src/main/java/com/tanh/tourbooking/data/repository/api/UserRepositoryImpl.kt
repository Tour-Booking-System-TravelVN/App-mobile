package com.tanh.tourbooking.data.repository.api

import com.tanh.tourbooking.domain.model.Customer
import com.tanh.tourbooking.domain.model.TourGuide
import com.tanh.tourbooking.domain.repository.api.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl : UserRepository {

    override suspend fun getUsers(): Flow<List<Customer>> {
        return flow {
            emit(users)
        }
    }

}

val users = listOf(
    Customer(username = "user1", password = "password1", id = 1),
    Customer(username = "user2", password = "password2", id = 2),
    Customer(username = "user3", password = "password3", id = 3),
    Customer(username = "user4", password = "password4", id = 4),
    Customer(username = "user5", password = "password5", id = 5),)

val tourGuide = TourGuide(1, "name", "email")

const val tourId = 1