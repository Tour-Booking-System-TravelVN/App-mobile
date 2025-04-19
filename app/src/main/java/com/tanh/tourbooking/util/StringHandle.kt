package com.tanh.tourbooking.util

fun String.toStringList(): List<String> {
    val list = this.split("\r\n").map { it.removePrefix("- ").trim() }
    return list
}

fun String.toFormattedDate(): String {
    val parts = this.split("-")
    val month = parts[1].padStart(2, '0')
    val day = parts[2].padStart(2, '0')
    return "${parts[0]}-$month-$day"
}