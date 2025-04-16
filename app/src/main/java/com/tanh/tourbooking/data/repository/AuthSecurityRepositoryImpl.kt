package com.tanh.tourbooking.data.repository

import androidx.datastore.core.DataStore
import com.tanh.tourbooking.data.model.dto.auth.AuthResult
import com.tanh.tourbooking.di.IODispatcher
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthSecurityRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<AuthResult>
): AuthSecurityRepository {
    override suspend fun updateData(data: AuthResult) {
        dataStore.updateData {
            data
        }
    }

    override suspend fun readData(): AuthResult {
        return dataStore.data.first()
    }
}