package com.tanh.tourbooking.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import com.tanh.tourbooking.data.model.dto.tour.ChatBoxDto
import com.tanh.tourbooking.data.model.dto.tour.MessageDto
import com.tanh.tourbooking.domain.model.ChatBox
import com.tanh.tourbooking.domain.model.Message
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Date

fun ChatBox.toChatBoxDto(): ChatBoxDto =
    ChatBoxDto(
        participants = participants,
        lastTimestamp = Timestamp.now(),
        message = message,
        adminId = adminId,
        chatId = chatId,
        name = name,
        uniqueBookingId = uniqueBookingId,
        waitingId = waitingId
    )

fun ChatBoxDto.toChatBox(): ChatBox =
    ChatBox(
        participants = participants,
        lastTime = lastTimestamp.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
        message = message,
        adminId = adminId,
        chatId = chatId,
        name = name,
        uniqueBookingId = uniqueBookingId,
        waitingId = waitingId
    )

fun Message.toMessageDto(): MessageDto =
    MessageDto(
        senderId = senderId,
        text = text,
        timestamp = Timestamp(Date.from(time.atZone(ZoneId.systemDefault()).toInstant())),
        senderName = senderName
    )

fun MessageDto.toMessage(): Message =
    Message(
        senderId = senderId,
        text = text,
        time = timestamp.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
        senderName = senderName
    )