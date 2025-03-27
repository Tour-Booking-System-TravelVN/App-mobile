package com.tanh.tourbooking.data.networking.api

import com.tanh.tourbooking.data.model.dto.fcm.FCMMessage
import com.tanh.tourbooking.util.AccessToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface FCMApi {

    @POST("v1/projects/noteapp-d7b38/messages:send")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun sendTopic(
        @Body message: FCMMessage,
        @Header("Authorization") accessToken: String = "Bearer ${AccessToken.getAccessToken()}"
    ): Call<Void>

}