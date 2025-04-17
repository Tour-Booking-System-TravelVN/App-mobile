package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.CategoryDto
import com.tanh.tourbooking.domain.model.Category

fun Category.toCategoryDto(): CategoryDto =
    CategoryDto(
        categoryName = categoryName,
        description = description,
        id = id
    )

fun CategoryDto.toCategory(): Category =
    Category(
        categoryName = categoryName,
        description = description,
        id = id
    )