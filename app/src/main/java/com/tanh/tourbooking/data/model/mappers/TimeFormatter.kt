package com.tanh.tourbooking.data.model.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


object TimeFormatter {
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(dateTime: LocalDateTime): String {
        val currentDateTime = LocalDateTime.now()

        return if (dateTime.toLocalDate() == currentDateTime.toLocalDate()) {
            dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
        } else {
            dateTime.format(DateTimeFormatter.ofPattern("dd/MM"))
        }
    }
}