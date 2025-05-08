package com.tanh.tourbooking.data.model.util.exception

enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN,
}

fun NetworkError.toMessage(): String = when(this) {
    NetworkError.REQUEST_TIMEOUT -> "Time out"
    NetworkError.TOO_MANY_REQUESTS -> "Too many requests"
    NetworkError.NO_INTERNET -> "No internet connection"
    NetworkError.SERVER_ERROR -> "500 Server error"
    NetworkError.SERIALIZATION -> "400 Serialization Error"
    NetworkError.UNKNOWN -> "Oops, something went wrong"
}

sealed class NetworkingError : Error {
    data object NoInternet : NetworkingError()
    data object ServerError: NetworkingError()
    data object Serialization : NetworkingError()
    data object Unknown : NetworkingError()
    data class ClientError(val code: Int, val message: String): NetworkingError()
}

fun NetworkingError.toMessage(): String = when(this) {
    is NetworkingError.ClientError -> this.message
    NetworkingError.NoInternet -> "Không có kết nối internet"
    NetworkingError.Serialization -> "Không thể serialization"
    NetworkingError.ServerError -> "Lỗi server"
    NetworkingError.Unknown -> "Lỗi không xác định"
}