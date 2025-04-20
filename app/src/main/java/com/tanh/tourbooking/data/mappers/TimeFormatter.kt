package com.tanh.tourbooking.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


object TimeFormatter {

    fun formatDate(dateTime: LocalDateTime): String {
        val currentDateTime = LocalDateTime.now()

        return if (dateTime.toLocalDate() == currentDateTime.toLocalDate()) {
            dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
        } else {
            dateTime.format(DateTimeFormatter.ofPattern("dd/MM"))
        }
    }

    fun formatTime(datetime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm")
        return datetime.format(formatter)
    }

    fun formatDateAndYear(dateTime: LocalDate): String {
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yy"))

    }

}


private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun LocalDateTime.toFormattedString(): String = this.format(formatter)
fun String.toLocalDateTime(): LocalDateTime = LocalDateTime.parse(this, formatter)

fun String.toLocalDate(): LocalDate = LocalDate.parse(this, dateFormatter)
fun LocalDate.toFormattedString(): String = this.format(dateFormatter)

fun String.isoToLocalDateTime(): LocalDateTime {
    return try {
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    } catch (e: Exception) {
        ZonedDateTime.parse(this).toLocalDateTime()
    }
}

