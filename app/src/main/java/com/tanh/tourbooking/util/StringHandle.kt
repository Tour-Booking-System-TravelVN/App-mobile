package com.tanh.tourbooking.util

fun String.toStringList(): List<String> {
    val list = this.split("\r\n").map { it.removePrefix("- ").trim() }
    return list
}