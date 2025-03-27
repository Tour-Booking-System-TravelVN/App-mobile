package com.tanh.tourbooking.domain.repository.firestore

interface UserTokenRepository {
    suspend fun getUserTokenById(userId: Int): String?
    suspend fun updateUserToken(userId: Int, token: String)
    suspend fun fetchAndSaveFCMToken(userId: Int)
}