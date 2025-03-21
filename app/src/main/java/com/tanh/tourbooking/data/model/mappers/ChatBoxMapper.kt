package com.tanh.tourbooking.data.model.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import com.tanh.tourbooking.data.model.dto.ChatBoxDto
import com.tanh.tourbooking.data.model.dto.MessageDto
import com.tanh.tourbooking.domain.model.ChatBox
import com.tanh.tourbooking.domain.model.Message
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
fun ChatBox.toChatBoxDto(): ChatBoxDto =
    ChatBoxDto(
        participants = participants,
        lastTimestamp = com.google.firebase.Timestamp.now(),
        message = message,
        adminId = adminId,
        chatId = chatId,
        name = name
    )

@RequiresApi(Build.VERSION_CODES.O)
fun ChatBoxDto.toChatBox(): ChatBox =
    ChatBox(
        participants = participants,
        lastTime = lastTimestamp.toDate().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime(),
        message = message,
        adminId = adminId,
        chatId = chatId,
        name = name
    )

@RequiresApi(Build.VERSION_CODES.O)
fun Message.toMessageDto(): MessageDto =
    MessageDto(
        senderId = senderId,
        text = text,
        timestamp = Timestamp(time.toEpochSecond(ZoneOffset.UTC), 0)
    )

@RequiresApi(Build.VERSION_CODES.O)
fun MessageDto.toMessage(): Message =
    Message(
        senderId = senderId,
        text = text,
        time = timestamp.toDate().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime()
    )