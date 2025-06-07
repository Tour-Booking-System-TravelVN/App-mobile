package com.tanh.tourbooking.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.tanh.tourbooking.data.model.dto.auth.AuthResult
import com.tanh.tourbooking.dataStore
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import com.tanh.tourbooking.domain.repository.api.AuthRepository
import com.tanh.tourbooking.domain.repository.api.BookingRepository
import com.tanh.tourbooking.domain.repository.api.PaymentRepository
import com.tanh.tourbooking.domain.repository.api.RatingRepository
import com.tanh.tourbooking.domain.repository.api.TourProgramRepository
import com.tanh.tourbooking.domain.repository.api.TourUnitCalendarRepository
import com.tanh.tourbooking.domain.repository.api.TourUnitRepository
import com.tanh.tourbooking.domain.repository.api.UserRepository
import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import com.tanh.tourbooking.domain.repository.firestore.MessageRepository
import com.tanh.tourbooking.domain.repository.firestore.NotificationHandler
import com.tanh.tourbooking.domain.usecase.auth.ChangePasswordInfoUseCase
import com.tanh.tourbooking.domain.usecase.auth.CheckRoleUseCase
import com.tanh.tourbooking.domain.usecase.auth.EncryptAuthResultUseCase
import com.tanh.tourbooking.domain.usecase.auth.GetInformationUseCase
import com.tanh.tourbooking.domain.usecase.tour.FoundTourUseCase
import com.tanh.tourbooking.domain.usecase.auth.LoginUseCase
import com.tanh.tourbooking.domain.usecase.auth.LogoutUseCase
import com.tanh.tourbooking.domain.usecase.auth.ReadAuthResultUseCase
import com.tanh.tourbooking.domain.usecase.auth.RegisterUseCase
import com.tanh.tourbooking.domain.usecase.auth.UpdateInforCustomerUseCase
import com.tanh.tourbooking.domain.usecase.auth.ValidTokenUseCase
import com.tanh.tourbooking.domain.usecase.chatbox.AcceptUserJoinChat
import com.tanh.tourbooking.domain.usecase.chatbox.AllowUserToChat
import com.tanh.tourbooking.domain.usecase.chatbox.ChatUseCaseManager
import com.tanh.tourbooking.domain.usecase.chatbox.CreateMessage
import com.tanh.tourbooking.domain.usecase.chatbox.GetChatBoxIdByTourUnitId
import com.tanh.tourbooking.domain.usecase.chatbox.NotifyMessage
import com.tanh.tourbooking.domain.usecase.chatbox.ObserveChat
import com.tanh.tourbooking.domain.usecase.chatbox.ObserveChatlist
import com.tanh.tourbooking.domain.usecase.chatbox.ObserveMessage
import com.tanh.tourbooking.domain.usecase.chatbox.ObserveWaitingChat
import com.tanh.tourbooking.domain.usecase.chatbox.ObserveWaitingId
import com.tanh.tourbooking.domain.usecase.chatbox.RecallMessage
import com.tanh.tourbooking.domain.usecase.chatbox.RefuseUserToChat
import com.tanh.tourbooking.domain.usecase.payment.CancelTourUseCase
import com.tanh.tourbooking.domain.usecase.payment.ConfirmPaymentUseCase
import com.tanh.tourbooking.domain.usecase.payment.CreatePaymentUseCase
import com.tanh.tourbooking.domain.usecase.payment.CreateZaloPaymentUseCase
import com.tanh.tourbooking.domain.usecase.payment.GetBookingIdUseCase
import com.tanh.tourbooking.domain.usecase.tour.CheckTourUnitUseCase
import com.tanh.tourbooking.domain.usecase.tour.CreateBookingOrderUseCase
import com.tanh.tourbooking.domain.usecase.tour.GetMyTourUseCase
import com.tanh.tourbooking.domain.usecase.tour.GetRatingByTourUnitIdUseCase
import com.tanh.tourbooking.domain.usecase.tour.GetTourProgramByTourIdUseCase
import com.tanh.tourbooking.domain.usecase.tour.GetTourUnitCalendarUseCase
import com.tanh.tourbooking.domain.usecase.tour.RatingTourUseCase
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
    fun provideChangePasswordUseCase(
        userRepository: UserRepository,
        authSecurityRepository: AuthSecurityRepository
    ) = ChangePasswordInfoUseCase(userRepository, authSecurityRepository)

    @Provides
    @Singleton
    fun provideUpdateInfoCustomerUseCase(
        userRepository: UserRepository,
        authSecurityRepository: AuthSecurityRepository
    ) = UpdateInforCustomerUseCase(userRepository, authSecurityRepository)

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideConfirmPaymentUseCase(
        authSecurityRepository: AuthSecurityRepository,
        paymentRepository: PaymentRepository
    ) = ConfirmPaymentUseCase(authSecurityRepository, paymentRepository)

    @Provides
    @Singleton
    fun provideGetOrderCodeUseCase(
        authSecurityRepository: AuthSecurityRepository,
        paymentRepository: PaymentRepository
    ) = GetBookingIdUseCase(authSecurityRepository, paymentRepository)

    @Provides
    @Singleton
    fun provideCancelTourUseCase(
        authSecurityRepository: AuthSecurityRepository,
        paymentRepository: PaymentRepository
    ) = CancelTourUseCase(authSecurityRepository, paymentRepository)

    @Provides
    @Singleton
    fun provideRatingTourUseCase(
        authSecurityRepository: AuthSecurityRepository,
        tourUnitRepository: TourUnitRepository
    ) = RatingTourUseCase(authSecurityRepository, tourUnitRepository)

    @Provides
    @Singleton
    fun provideGetMyTourUseCase(
        authSecurityRepository: AuthSecurityRepository,
        tourUnitRepository: TourUnitRepository
    ) = GetMyTourUseCase(authSecurityRepository, tourUnitRepository)

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        authSecurityRepository: AuthSecurityRepository,
        authRepository: AuthRepository
    ) = LogoutUseCase(authSecurityRepository, authRepository)

    @Provides
    @Singleton
    fun provideValidTokenUseCase(
        authSecurityRepository: AuthSecurityRepository,
        authRepository: AuthRepository
    ) = ValidTokenUseCase(authSecurityRepository, authRepository)

    @Provides
    @Singleton
    fun provideCreateZaloPaymentUseCase(
        authSecurityRepository: AuthSecurityRepository,
        paymentRepository: PaymentRepository
    ) = CreateZaloPaymentUseCase(authSecurityRepository, paymentRepository)


    @Provides
    @Singleton
    fun provideCreatePaymentUseCase(
        authSecurityRepository: AuthSecurityRepository,
        paymentRepository: PaymentRepository
    ) = CreatePaymentUseCase(authSecurityRepository, paymentRepository)

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
        FoundTourUseCase(repository)

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
    fun provideObserveWaitingChat(repository: ChatRepository) = ObserveWaitingChat(repository)

    @Provides
    @Singleton
    fun provideObserveWaitingIdi(repository: ChatRepository) = ObserveWaitingId(repository)

    @Provides
    @Singleton
    fun provideAcceptUserJoinChat(repository: ChatRepository) = AcceptUserJoinChat(repository)

    @Provides
    @Singleton
    fun provideRefuseUserToChat(repository: ChatRepository) = RefuseUserToChat(repository)

    @Provides
    @Singleton
    fun provideRecallMessage(repository: MessageRepository) = RecallMessage(repository)

    @Provides
    @Singleton
    fun getChatIdByBookingId(repository: ChatRepository) = GetChatBoxIdByTourUnitId(repository)

    @Provides
    @Singleton
    fun provideChatManager(
        observeChat: ObserveChat,
        createMessage: CreateMessage,
        observeChatlist: ObserveChatlist,
        observeMessage: ObserveMessage,
        allowUserToChat: AllowUserToChat,
        notifyMessage: NotifyMessage,
        observeWaitingChat: ObserveWaitingChat,
        observeWaitingId: ObserveWaitingId,
        acceptUserJoinChat: AcceptUserJoinChat,
        refuseUserToChat: RefuseUserToChat,
        recallMessage: RecallMessage,
        getChatBoxIdByTourUnitId: GetChatBoxIdByTourUnitId
    ) =
        ChatUseCaseManager(
            observeChat,
            createMessage,
            observeChatlist,
            observeMessage,
            allowUserToChat,
            notifyMessage,
            observeWaitingChat,
            observeWaitingId,
            acceptUserJoinChat,
            refuseUserToChat,
            recallMessage,
            getChatBoxIdByTourUnitId
        )

}