package com.tanh.tourbooking.di

import com.google.firebase.firestore.FirebaseFirestore
import com.tanh.tourbooking.data.repository.api.UserRepositoryImpl
import com.tanh.tourbooking.domain.repository.api.UserRepository
import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import com.tanh.tourbooking.domain.repository.firestore.MessageRepository
import com.tanh.tourbooking.domain.usecase.chatbox.AllowUserToChat
import com.tanh.tourbooking.domain.usecase.chatbox.ChatUseCaseManager
import com.tanh.tourbooking.domain.usecase.chatbox.CreateMessage
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

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

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
    fun provideAllowUserToChat(repositoryImpl: UserRepository, chatRepository: ChatRepository) =
        AllowUserToChat(repositoryImpl, chatRepository)

    @Provides
    @Singleton
    fun provideChatManager(
        observeChat: ObserveChat,
        createMessage: CreateMessage,
        observeChatlist: ObserveChatlist,
        observeMessage: ObserveMessage,
        allowUserToChat: AllowUserToChat
    ) =
        ChatUseCaseManager(
            observeChat,
            createMessage,
            observeChatlist,
            observeMessage,
            allowUserToChat
        )

}