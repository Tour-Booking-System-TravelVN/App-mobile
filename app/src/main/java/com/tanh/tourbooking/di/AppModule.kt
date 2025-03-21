package com.tanh.tourbooking.di

import com.google.firebase.firestore.FirebaseFirestore
import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import com.tanh.tourbooking.domain.repository.firestore.MessageRepository
import com.tanh.tourbooking.domain.usecase.chatbox.ChatUseCaseManager
import com.tanh.tourbooking.domain.usecase.chatbox.CreateMessage
import com.tanh.tourbooking.domain.usecase.chatbox.ObserveChat
import com.tanh.tourbooking.domain.usecase.chatbox.ObserveChatlist
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
    fun provideChatManager(
        observeChat: ObserveChat,
        createMessage: CreateMessage,
        observeChatlist: ObserveChatlist
    ) =
        ChatUseCaseManager(observeChat, createMessage, observeChatlist)

}