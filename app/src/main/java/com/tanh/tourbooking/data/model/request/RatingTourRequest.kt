package com.tanh.tourbooking.data.model.request

data class RatingTourRequest(
    val tourUnitId: String,
    val ratingValue: Int,
    val comment: String
)