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