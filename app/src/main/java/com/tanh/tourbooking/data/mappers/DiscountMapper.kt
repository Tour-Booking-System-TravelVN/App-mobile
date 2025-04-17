package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.DiscountDto
import com.tanh.tourbooking.domain.model.Discount

fun Discount.toDiscountDto(): DiscountDto =
    DiscountDto(
        discountName = discountName,
        discountUnit = discountUnit,
        discountValue = discountValue,
        id = id
    )

fun DiscountDto.toDiscount(): Discount =
    Discount(
        discountName = discountName,
        discountUnit = discountUnit,
        discountValue = discountValue,
        id = id
    )