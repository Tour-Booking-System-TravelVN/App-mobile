package com.tanh.tourbooking.data.networking.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.tanh.tourbooking.data.model.util.exception.NetworkingError
import com.tanh.tourbooking.data.model.util.exception.Result
import okio.IOException
import retrofit2.Response

data class SuccessResponse<T> (
    val code: Int,
    val result: T
)

data class ErrorResponse(
    val code: Int,
    val message: String
)

suspend fun <T: Any> safeCallWith400(
    execute: suspend () -> Response<SuccessResponse<T>>,
    gson: Gson
): Result<T, NetworkingError> {
    return try {
        val response = execute()
        when(response.code()) {
            200 -> {
                response.body()?.let {
                    Result.Success(it.result)
                } ?: Result.Error(NetworkingError.Unknown)
            }
            400 -> {
                val errorJson = response.errorBody()?.string()
                if(!errorJson.isNullOrBlank()) {
                    try {
                        val error = gson.fromJson<ErrorResponse>(errorJson, ErrorResponse::class.java)
                        Result.Error(NetworkingError.ClientError(code = error.code, message = error.message))
                    } catch (e: JsonSyntaxException) {
                        Result.Error(NetworkingError.Serialization)
                    }
                } else {
                    Result.Error(NetworkingError.Unknown)
                }
            }
            else -> Result.Error(NetworkingError.ServerError)
        }
    } catch (e: IOException) {
        Result.Error(NetworkingError.NoInternet)
    } catch (e: Exception) {
        Result.Error(NetworkingError.Unknown)
    }
}