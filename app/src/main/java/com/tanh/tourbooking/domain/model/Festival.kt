package com.tanh.tourbooking.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Festival(
    val description: String,
    val displayStatus: Boolean,
    val festivalName: String,
    val id: Int
)