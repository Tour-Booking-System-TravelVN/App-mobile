package com.tanh.tourbooking.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val categoryName: String,
    val description: String,
    val id: Int
)