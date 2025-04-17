package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.CustomerDto
import com.tanh.tourbooking.domain.model.Information

fun Information.Customer.toCustomerDto(): CustomerDto =
    CustomerDto(
        address = address,
        citizenId = citizenId,
        dateOfBirth = dateOfBirth,
        firstname = firstname,
        gender = gender,
        id = id,
        lastname = lastname,
        nationality = nationality,
        note = note,
        passport = passport,
        phoneNumber = phoneNumber
    )

fun CustomerDto.toCustomer(): Information.Customer =
    Information.Customer(
        address = address,
        citizenId = citizenId,
        dateOfBirth = dateOfBirth,
        firstname = firstname,
        gender = gender,
        id = id,
        lastname = lastname,
        nationality = nationality,
        note = note,
        passport = passport,
        phoneNumber = phoneNumber
    )