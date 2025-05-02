package com.tanh.tourbooking.data.networking.api

import com.tanh.tourbooking.data.model.dto.auth.AuthResponse
import com.tanh.tourbooking.data.model.dto.auth.LoginRequest
import com.tanh.tourbooking.data.model.dto.auth.RegisterRequest
import com.tanh.tourbooking.data.model.request.CreatePaymentRequest
import com.tanh.tourbooking.data.model.request.PaymentRequest
import com.tanh.tourbooking.data.model.request.RatingTourRequest
import com.tanh.tourbooking.data.model.response.AvailableMonthResponse
import com.tanh.tourbooking.data.model.response.BookingResponse
import com.tanh.tourbooking.data.model.response.CheckTourUnitResponse
import com.tanh.tourbooking.data.model.response.CreatePaymentResponse
import com.tanh.tourbooking.data.model.response.GetTourResponse
import com.tanh.tourbooking.data.model.response.LogoutResponse
import com.tanh.tourbooking.data.model.response.RatingResponse
import com.tanh.tourbooking.data.model.response.RatingTourResponse
import com.tanh.tourbooking.data.model.response.TourProgramResponse
import com.tanh.tourbooking.data.model.response.TourUnitByPlaceResponse
import com.tanh.tourbooking.data.model.response.TourUnitCalendarResponse
import com.tanh.tourbooking.data.model.response.UserInformationResponse
import com.tanh.tourbooking.data.model.response.ValidTokenResponse
import com.tanh.tourbooking.domain.model.Rating
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("/rating/tour-detail/{tourUnitId}")
    suspend fun getRatingByTourUnitId(@Path("tourUnitId") tourUnitId: String): Response<RatingResponse>

    @GET("/program/tour-detail/{tourUnitId}")
    suspend fun getTourProgramByTourUnitId(@Path("tourUnitId") tourUnitId: String): Response<TourProgramResponse>

    @GET("/tour/calendar/{tourId}")
    suspend fun getAvailableMonthByTourId(@Path("tourId") tourId: String): Response<AvailableMonthResponse>

    @GET("/tourunit/calendar")
    suspend fun getTourUnitCalendar(
        @Query("month") month: Int,
        @Query("year") year: Int,
        @Query("tourid") tourId: String
    ): Response<TourUnitCalendarResponse>

    @POST("/order/create")
    suspend fun createBooking(
        @Header("Authorization") token: String,
        @Body request: PaymentRequest
    ): Response<BookingResponse>

    @GET("booking/checkbeforebooking")
    suspend fun checkBeforeBooking(
        @Header("Authorization") token: String,
        @Query("tourUnitId") tourUnitId: String
    ): Response<CheckTourUnitResponse>

    @POST("/order/create")
    suspend fun createPayment(
        @Header("Authorization") token: String,
        @Body request: CreatePaymentRequest
    ): Response<CreatePaymentResponse>

    @POST("/auth/introspect")
    suspend fun validToken(
        @Header("Authorization") token: String
    ): Response<ValidTokenResponse>

    @POST("/auth/logoutapp")
    suspend fun logout(
        @Header("Authorization") token: String
    ): Response<LogoutResponse>

    @GET("/booking/mytours")
    suspend fun getMyTours(
        @Header("Authorization") token: String,
        @Query("status") status: String,
        @Query("page") page: Int
    ): Response<GetTourResponse>

    @POST("rating/rating-tour")
    suspend fun ratingTour(
        @Header("Authorization") token: String,
        @Body request: RatingTourRequest
    ): Response<RatingTourResponse>

}