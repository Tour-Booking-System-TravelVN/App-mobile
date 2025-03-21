package com.tanh.tourbooking.di

import com.tanh.tourbooking.data.repository.api.UserRepositoryImpl
import com.tanh.tourbooking.data.repository.firestore.ChatRepositoryImpl
import com.tanh.tourbooking.data.repository.firestore.MessageRepositoryImpl
import com.tanh.tourbooking.domain.repository.api.UserRepository
import com.tanh.tourbooking.domain.repository.firestore.ChatRepository
import com.tanh.tourbooking.domain.repository.firestore.MessageRepository
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
    abstract fun provideUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun provideChatRepository(impl: ChatRepositoryImpl): ChatRepository

    @Binds
    @Singleton
    abstract fun provideMessageRepository(impl: MessageRepositoryImpl): MessageRepository

}