package com.tanh.tourbooking.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.tanh.tourbooking.data.repository.api.UserRepositoryImpl
import com.tanh.tourbooking.domain.repository.api.AuthRepository
import com.tanh.tourbooking.domain.repository.api.UserRepository
import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import com.tanh.tourbooking.domain.repository.firestore.MessageRepository
import com.tanh.tourbooking.domain.repository.firestore.NotificationHandler
import com.tanh.tourbooking.domain.usecase.auth.LoginUseCase
import com.tanh.tourbooking.domain.usecase.auth.RegisterUseCase
import com.tanh.tourbooking.domain.usecase.chatbox.AllowUserToChat
import com.tanh.tourbooking.domain.usecase.chatbox.ChatUseCaseManager
import com.tanh.tourbooking.domain.usecase.chatbox.CreateMessage
import com.tanh.tourbooking.domain.usecase.chatbox.NotifyMessage
import com.tanh.tourbooking.domain.usecase.chatbox.ObserveChat
import com.tanh.tourbooking.domain.usecase.chatbox.ObserveChatlist
import com.tanh.tourbooking.domain.usecase.chatbox.ObserveMessage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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