package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.RatingDto
import com.tanh.tourbooking.domain.model.Rating

fun Rating.toRatingDto(): RatingDto =
    RatingDto(
        comment = comment,
        fullName = fullName,
        id = id,
        ratingValue = ratingValue,
        status = status
    )

fun RatingDto.toRating(): Rating =
    Rating(
        comment = comment,
        fullName = fullName,
        id = id,
        ratingValue = ratingValue,
        status = status
    )