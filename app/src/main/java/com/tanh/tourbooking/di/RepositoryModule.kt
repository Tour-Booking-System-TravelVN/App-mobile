package com.tanh.tourbooking.di

import com.tanh.tourbooking.data.repository.AuthSecurityRepositoryImpl
import com.tanh.tourbooking.data.repository.api.AuthRepositoryImpl
import com.tanh.tourbooking.data.repository.api.BookingRepositoryImpl
import com.tanh.tourbooking.data.repository.api.RatingRepositoryImpl
import com.tanh.tourbooking.data.repository.api.TourProgramRepositoryImpl
import com.tanh.tourbooking.data.repository.api.TourUnitCalendarRepositoryImpl
import com.tanh.tourbooking.data.repository.api.TourUnitRepositoryImpl
import com.tanh.tourbooking.data.repository.api.UserRepositoryImpl
import com.tanh.tourbooking.data.repository.firestore.ChatRepositoryImpl
import com.tanh.tourbooking.data.repository.firestore.MessageRepositoryImpl
import com.tanh.tourbooking.data.repository.firestore.NotificationHandlerImpl
import com.tanh.tourbooking.data.repository.firestore.UserTokenRepositoryImpl
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import com.tanh.tourbooking.domain.repository.api.AuthRepository
import com.tanh.tourbooking.domain.repository.api.BookingRepository
import com.tanh.tourbooking.domain.repository.api.RatingRepository
import com.tanh.tourbooking.domain.repository.api.TourProgramRepository
import com.tanh.tourbooking.domain.repository.api.TourUnitCalendarRepository
import com.tanh.tourbooking.domain.repository.api.TourUnitRepository
import com.tanh.tourbooking.domain.repository.api.UserRepository
import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import com.tanh.tourbooking.domain.repository.firestore.MessageRepository
import com.tanh.tourbooking.domain.repository.firestore.NotificationHandler
import com.tanh.tourbooking.domain.repository.firestore.UserTokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideBookingRepository(impl: BookingRepositoryImpl): BookingRepository

    @Binds
    @Singleton
    abstract fun provideTourUnitCalendarRepository(impl: TourUnitCalendarRepositoryImpl): TourUnitCalendarRepository

    @Binds
    @Singleton
    abstract fun provideRatingRepository(impl: RatingRepositoryImpl): RatingRepository

    @Binds
    @Singleton
    abstract fun provideTourProgramRepository(impl: TourProgramRepositoryImpl): TourProgramRepository

    @Binds
    @Singleton
    abstract fun provideTourUnitRepository(impl: TourUnitRepositoryImpl): TourUnitRepository

    @Binds
    @Singleton
    abstract fun provideAuthSecurityRepository(impl: AuthSecurityRepositoryImpl): AuthSecurityRepository

    @Binds
    @Singleton
    abstract fun provideUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun provideChatRepository(impl: ChatRepositoryImpl): ChatRepository

    @Binds
    @Singleton
    abstract fun provideMessageRepository(impl: MessageRepositoryImpl): MessageRepository

    @Binds
    @Singleton
    abstract fun provideUserTokenRepository(impl: UserTokenRepositoryImpl): UserTokenRepository

    @Binds
    @Singleton
    abstract fun provideNotificationHandler(impl: NotificationHandlerImpl): NotificationHandler

    @Binds
    @Singleton
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

}