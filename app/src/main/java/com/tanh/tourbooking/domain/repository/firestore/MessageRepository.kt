package com.tanh.tourbooking.domain.repository.firestore

import com.tanh.tourbooking.data.model.dto.tour.MessageDto
import com.tanh.tourbooking.data.model.util.exception.Resources
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    fun observeMessages(chatId: String): Flow<Resources<List<MessageDto>, Exception>>
    suspend fun sendMessage(chatId: String, message: MessageDto)

}