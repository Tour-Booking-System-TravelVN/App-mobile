package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.ImageSetDto
import com.tanh.tourbooking.domain.model.ImageSet

fun ImageSet.toImageSetDto(): ImageSetDto =
    ImageSetDto(
        id = id,
        imageName = imageName,
        url = url
    )

fun ImageSetDto.toImageSet(): ImageSet =
    ImageSet(
        id = id,
        imageName = imageName,
        url = url
    )