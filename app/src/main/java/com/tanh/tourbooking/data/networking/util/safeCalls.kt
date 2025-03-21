package com.tanh.tourbooking.data.networking.util

import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException

suspend fun <T: Any> safeCall(
    execute: suspend() -> Response<T>
): Result<T, NetworkError> {
    return try {
        val response = execute()

        if(response.isSuccessful)  {
            response.body()?.let {
                Result.Success(it)
            } ?: Result.Error(NetworkError.UNKNOWN)
        } else {
            Result.Error(NetworkError.SERVER_ERROR)
        }
    } catch (e: UnresolvedAddressException) {
        Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        Result.Error(NetworkError.UNKNOWN)
    }
}