package com.tanh.tourbooking

import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val messages = listOf(
            LocalDateTime.of(2025, 3, 21, 10, 30),
            LocalDateTime.of(2025, 3, 21, 15, 45),
            LocalDateTime.of(2025, 3, 22, 9, 10),
            LocalDateTime.of(2025, 3, 22, 20, 5),
            LocalDateTime.of(2025, 3, 23, 8, 0)
        )

        val groupedByDate = messages.groupBy { it.toLocalDate() }
        groupedByDate.forEach { (date, times) ->
            println("📅 Ngày: $date")
            times.forEach { println("  🕒 ${it.toLocalTime()}") }
        }
    }
}