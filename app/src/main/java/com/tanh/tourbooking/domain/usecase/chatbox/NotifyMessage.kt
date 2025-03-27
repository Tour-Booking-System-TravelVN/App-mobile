package com.tanh.tourbooking.domain.usecase.chatbox

import com.tanh.tourbooking.domain.repository.firestore.NotificationHandler
import javax.inject.Inject

class NotifyMessage @Inject constructor(
    private val handler: NotificationHandler
) {

    suspend operator fun invoke(chatId: String, message: String, namegroup: String) {
        handler.sendNotificationByTopic(
            topic = chatId,
            title = namegroup,
            body = message
        )
    }

}