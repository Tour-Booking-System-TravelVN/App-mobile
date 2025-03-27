package com.tanh.tourbooking.domain.repository.firestore

import com.tanh.tourbooking.data.model.dto.ChatBoxDto
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.domain.model.ChatBox
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun createChatBox(chatBox: ChatBox)
    fun observeChatBox(chatId: String): Flow<Resources<ChatBoxDto, Exception>>
    suspend fun deleteInactiveChatBox(chatId: String)
    fun observeChatboxList(userId: Int): Flow<Resources<List<ChatBoxDto>, Exception>>
    suspend fun joinChatBox(uniqueBookingId: Int, userId: Int): String?
}