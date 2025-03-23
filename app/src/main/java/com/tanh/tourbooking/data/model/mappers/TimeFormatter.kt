package com.tanh.tourbooking.data.model.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import java.time.LocalDate
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(datetime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm")
        return datetime.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateAndYear(dateTime: LocalDate): String {
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yy"))

    }

}