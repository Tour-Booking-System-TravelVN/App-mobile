package com.tanh.tourbooking.di

import com.tanh.tourbooking.data.networking.api.AuthApi
import com.tanh.tourbooking.data.networking.api.FCMApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = okHttpClient

    //auth retrofit
    @Provides
    @Singleton
    @Named("Auth")
    fun provideAuthRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://192.168.229.46:8080")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    //auth fcm
    @Singleton
    @Provides
    @Named("FCM")
    fun provideFCMRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideAuthApi(@Named("Auth") retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideFCMApi(@Named("FCM") retrofit: Retrofit): FCMApi =
        retrofit.create(FCMApi::class.java)

}