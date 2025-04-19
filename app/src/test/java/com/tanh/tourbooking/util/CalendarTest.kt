package com.tanh.tourbooking.util

import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test

class CalendarTest {

    @Test
    fun `check days in september in 2025`() {
        val days = Calendar.getDaysInMonth(9, 2025)
        Truth.assertThat(days).isEqualTo(30)
    }

    @Test
    fun `check days in march in 2025`() {
        val days = Calendar.getDaysInMonth(3, 2025)
        Truth.assertThat(days).isEqualTo(31)
    }

    @Test
    fun `check days in february in 2024`() {
        val days = Calendar.getDaysInMonth(2, 2024)
        Truth.assertThat(days).isEqualTo(29)
    }

    @Test
    fun testGenerateYearData_nonLeapYear() {
        val result = Calendar.generateYearData(10, 2023)

        assertEquals(3, result.size)

        val october = result[0]
        assertEquals("Tháng 10", october.name)
        assertEquals(2023, october.year)
        assertEquals(10, october.month)
        assertEquals(31, october.days.size)
        assertEquals(1, october.days.first().date)
        assertEquals(false, october.days.first().data)
    }

    @Test
    fun testGenerateYearData_leapYear() {
        val result = Calendar.generateYearData(2, 2024)

        val february = result[0]
        assertEquals("Tháng 2", february.name)
        assertEquals(29, february.days.size)
        assertEquals(1, february.days.first().date)
        assertEquals(false, february.days.first().data)
    }

    @Test
    fun testGenerateYearData_startFromDecember() {
        val result = Calendar.generateYearData(12, 2025)

        assertEquals(1, result.size)
        assertEquals(12, result[0].month)
    }

}