package com.tanh.tourbooking.domain.repository.firestore

import com.tanh.tourbooking.data.model.dto.tour.ChatBoxDto
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.domain.model.ChatBox
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun createChatBox(chatBox: ChatBox)
    fun observeChatBox(chatId: String): Flow<Resources<ChatBoxDto, Exception>>
    suspend fun deleteInactiveChatBox(chatId: String)
    fun observeChatboxList(userId: Int): Flow<Resources<List<ChatBoxDto>, Exception>>
    suspend fun joinChatBox(uniqueBookingId: String, userId: Int): String?
    fun observeWaitingChatBoxList(userId: Int): Flow<Resources<List<ChatBoxDto>, Exception>>
    suspend fun observeWaitingId(chatId: String): Resources<List<Int>, Exception>
    suspend fun acceptUserIdToChat(userId: Int, chatId: String)
    suspend fun refureUserToChat(userId: Int, chatId: String)
    suspend fun getChatBoxIdByBookingId(bookingId: String): String?
}