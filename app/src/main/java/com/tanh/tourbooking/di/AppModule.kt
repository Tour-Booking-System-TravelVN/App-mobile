package com.tanh.tourbooking.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.tanh.tourbooking.data.model.dto.auth.AuthResult
import com.tanh.tourbooking.dataStore
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
import com.tanh.tourbooking.domain.usecase.auth.CheckRoleUseCase
import com.tanh.tourbooking.domain.usecase.auth.EncryptAuthResultUseCase
import com.tanh.tourbooking.domain.usecase.auth.GetInformationUseCase
import com.tanh.tourbooking.domain.usecase.tour.GetToursByPlaceUseCase
import com.tanh.tourbooking.domain.usecase.auth.LoginUseCase
import com.tanh.tourbooking.domain.usecase.auth.ReadAuthResultUseCase
import com.tanh.tourbooking.domain.usecase.auth.RegisterUseCase
import com.tanh.tourbooking.domain.usecase.chatbox.AllowUserToChat
import com.tanh.tourbooking.domain.usecase.chatbox.ChatUseCaseManager
import com.tanh.tourbooking.domain.usecase.chatbox.CreateMessage
import com.tanh.tourbooking.domain.usecase.chatbox.NotifyMessage
import com.tanh.tourbooking.domain.usecase.chatbox.ObserveChat
import com.tanh.tourbooking.domain.usecase.chatbox.ObserveChatlist
import com.tanh.tourbooking.domain.usecase.chatbox.ObserveMessage
import com.tanh.tourbooking.domain.usecase.tour.CheckTourUnitUseCase
import com.tanh.tourbooking.domain.usecase.tour.CreateBookingOrderUseCase
import com.tanh.tourbooking.domain.usecase.tour.GetRatingByTourUnitIdUseCase
import com.tanh.tourbooking.domain.usecase.tour.GetTourProgramByTourIdUseCase
import com.tanh.tourbooking.domain.usecase.tour.GetTourUnitCalendarUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCheckBeforeBookingUseCase(
        repository: AuthSecurityRepository,
        bookingRepository: BookingRepository
    ) = CheckTourUnitUseCase(repository, bookingRepository)

    @Provides
    @Singleton
    fun provideCreateBookingOrderUseCase(
        repository: AuthSecurityRepository,
        bookingRepository: BookingRepository
    ) = CreateBookingOrderUseCase(bookingRepository, repository)

    @Provides
    @Singleton
    fun provideGetTourUnitCalendar(repository: TourUnitCalendarRepository) =
        GetTourUnitCalendarUseCase(repository)

    @Provides
    @Singleton
    fun provideGetRatingByTourIdUseCase(repository: RatingRepository) =
        GetRatingByTourUnitIdUseCase(repository)

    @Provides
    @Singleton
    fun provideGetTourProgramByTourIdUseCase(repository: TourProgramRepository) =
        GetTourProgramByTourIdUseCase(repository)

    @Provides
    @Singleton
    fun provideGetToursByPlaceUseCase(repository: TourUnitRepository) =
        GetToursByPlaceUseCase(repository)

    @Provides
    @Singleton
    fun provideGetInformationUseCase(
        repository: UserRepository,
        authSecurityRepository: AuthSecurityRepository
    ) =
        GetInformationUseCase(repository, authSecurityRepository)

    @Provides
    @Singleton
    fun provideCheckRoleUseCase(repository: AuthSecurityRepository) = CheckRoleUseCase(repository)

    @Provides
    @Singleton
    fun provideEncryptAuthResultUseCase(repository: AuthSecurityRepository) =
        EncryptAuthResultUseCase(repository)

    @Provides
    @Singleton
    fun provideReadAuthResultUseCase(repository: AuthSecurityRepository) =
        ReadAuthResultUseCase(repository)


    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<AuthResult> {
        return context.dataStore
    }

    //registerusecase
    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthRepository) = RegisterUseCase(repository)

    //loginusecase
    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository) = LoginUseCase(repository)

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()

    @Provides
    @Singleton
    fun provideObserveChatUseCase(repository: ChatRepository) = ObserveChat(repository)

    @Provides
    @Singleton
    fun provideCreateMessageUseCase(repository: MessageRepository) = CreateMessage(repository)

    @Provides
    @Singleton
    fun provideObserveChatlist(repository: ChatRepository) = ObserveChatlist(repository)

    @Provides
    @Singleton
    fun provideObserveMessage(repository: MessageRepository) = ObserveMessage(repository)

    @Provides
    @Singleton
    fun provideAllowUserToChat(
        repositoryImpl: UserRepository,
        chatRepository: ChatRepository,
        handler: NotificationHandler
    ) =
        AllowUserToChat(repositoryImpl, chatRepository, handler)

    @Provides
    @Singleton
    fun provideNotifyMessage(handler: NotificationHandler) = NotifyMessage(handler)

    @Provides
    @Singleton
    fun provideChatManager(
        observeChat: ObserveChat,
        createMessage: CreateMessage,
        observeChatlist: ObserveChatlist,
        observeMessage: ObserveMessage,
        allowUserToChat: AllowUserToChat,
        notifyMessage: NotifyMessage
    ) =
        ChatUseCaseManager(
            observeChat,
            createMessage,
            observeChatlist,
            observeMessage,
            allowUserToChat,
            notifyMessage
        )

}