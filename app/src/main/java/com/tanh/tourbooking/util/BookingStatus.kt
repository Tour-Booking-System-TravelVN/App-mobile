package com.tanh.tourbooking.util

import androidx.compose.ui.graphics.Color

enum class BookingStatus(val code: String, val description: String, val color: Color) {
    P("P", "Chờ xử lý", Color(0xFFFFA500)),
    O("O", "Đang diễn ra", Color(0xFF2196F3)),
    C("C", "Hủy", Color(0xFFF44336)),
    D("D", "Hoàn thành", Color(0xFF4CAF50)),
    W("W", "Chờ xử lý", Color(0xFFFFC107)),
    E("E", "Expired", Color(0xFF9E9E9E)),
    H("H", "Hold", Color(0xFF673AB7));

    companion object {
        fun fromCode(code: String): BookingStatus? = entries.find { it.code == code }
    }
}