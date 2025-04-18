package com.tanh.tourbooking.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageSet(
    val id: Int,
    val imageName: String,
    val url: String
)