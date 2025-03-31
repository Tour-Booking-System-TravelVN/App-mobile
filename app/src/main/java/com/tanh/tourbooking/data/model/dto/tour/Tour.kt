package com.tanh.tourbooking.data.model.dto.tour

data class Tour(
    val name: String,
    val description: String,
    val rated: Int,
    val totalRate: Int,
    val price: Double,
)