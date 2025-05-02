package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.MyTourDto
import com.tanh.tourbooking.domain.model.MyTour

fun MyTourDto.toMyTour(): MyTour =
    MyTour(
        adultNumber = adultNumber,
        babyNumber = babyNumber,
        bookingDate = bookingDate.isoToLocalDateTime(),
        bookingId = bookingId,
        customer = c?.toCustomer(),
        childNumber = childNumber,
        companionCustomerSet = companionCustomerSet,
        note = note,
        paymentId = paymentId,
        privateRoomNumber = privateRoomNumber,
        status = status,
        toddlerNumber = toddlerNumber,
        totalAmount = totalAmount,
        tourUnit = tourUnit.toTourUnit()
    )