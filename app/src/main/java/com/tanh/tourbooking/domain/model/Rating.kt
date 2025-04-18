package com.tanh.tourbooking.domain.model

data class Rating (
    val comment: String,
    val fullName: String,
    val id: Int,
    val ratingValue: Int,
    val status: String
)