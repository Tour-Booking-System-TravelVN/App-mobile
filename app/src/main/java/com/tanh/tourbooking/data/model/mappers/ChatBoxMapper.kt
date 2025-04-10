package com.tanh.tourbooking.data.model.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import com.tanh.tourbooking.data.model.dto.ChatBoxDto
import com.tanh.tourbooking.data.model.dto.MessageDto
import com.tanh.tourbooking.domain.model.ChatBox
import com.tanh.tourbooking.domain.model.Message
import java.time.ZoneId
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
fun ChatBox.toChatBoxDto(): ChatBoxDto =
    ChatBoxDto(
        participants = participants,
        lastTimestamp = com.google.firebase.Timestamp.now(),
        message = message,
        adminId = adminId,
        chatId = chatId,
        name = name,
        uniqueBookingId = uniqueBookingId
    )

@RequiresApi(Build.VERSION_CODES.O)
fun ChatBoxDto.toChatBox(): ChatBox =
    ChatBox(
        participants = participants,
        lastTime = lastTimestamp.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
        message = message,
        adminId = adminId,
        chatId = chatId,
        name = name,
        uniqueBookingId = uniqueBookingId
    )

@RequiresApi(Build.VERSION_CODES.O)
fun Message.toMessageDto(): MessageDto =
    MessageDto(
        senderId = senderId,
        text = text,
        timestamp = Timestamp(time.toEpochSecond(ZoneId.systemDefault() as ZoneOffset), 0),
        senderName = senderName
    )

@RequiresApi(Build.VERSION_CODES.O)
fun MessageDto.toMessage(): Message =
    Message(
        senderId = senderId,
        text = text,
        time = timestamp.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
        senderName = senderName
    )