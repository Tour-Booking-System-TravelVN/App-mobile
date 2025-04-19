package com.tanh.tourbooking.util

import java.time.LocalDate

data class Day(val date: Int, val data: Boolean = false)
data class Month(val name: String, val year: Int, val month: Int, val days: List<Day>)


object Calendar {

    fun getDaysInMonth(month: Int, year: Int): Int {
        return when(month) {
            2 -> if((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
            1, 3, 5, 8, 10 -> 31
            else -> 30
        }
    }

    fun generateYearData(startMonth: Int, year: Int): List<Month> {
        return (startMonth..12).map { month ->
            val daysInMonth = getDaysInMonth(month, year)
            val days = (1..daysInMonth).map { Day(it) }
            Month(name = "Tháng $month", year, month, days)
        }
    }

    fun getFirstDaysOfWeek(year: Int, month: Int): Int {
        val date = LocalDate.of(year, month, 1)
        val dayOfWeek = date.dayOfWeek.value
        return if (dayOfWeek == 7) 0 else dayOfWeek
    }

}