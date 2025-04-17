package com.tanh.tourbooking.data.networking.api

import com.tanh.tourbooking.data.model.dto.auth.AuthResponse
import com.tanh.tourbooking.data.model.dto.auth.LoginRequest
import com.tanh.tourbooking.data.model.dto.auth.RegisterRequest
import com.tanh.tourbooking.data.model.response.TourUnitByPlaceResponse
import com.tanh.tourbooking.data.model.response.UserInformationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TourBookingApi {

    @POST("/auth/tokenapp")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("/registerapp")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @GET("/customer/myinfo")
    suspend fun getInformation(@Header("Authorization") token: String): Response<UserInformationResponse>

    @GET("/tourunit/foundtourlist")
    suspend fun findTourByPlace(
        @Query("keywords") place: String,
        @Query("price") price: String,
        @Query("departure_date") departureDate: String? = null,
        @Query("page") page: Int? = null
        ): Response<TourUnitByPlaceResponse>

}