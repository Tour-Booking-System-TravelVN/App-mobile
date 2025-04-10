package com.tanh.tourbooking.domain.usecase.chatbox

import javax.inject.Inject

class ChatUseCaseManager @Inject constructor(
    val observeChat: ObserveChat,
    val createMessage: CreateMessage,
    val observeChatlist: ObserveChatlist,
    val observeMessage: ObserveMessage,
    val allowUserToChat: AllowUserToChat,
    val notifyMessage: NotifyMessage
)