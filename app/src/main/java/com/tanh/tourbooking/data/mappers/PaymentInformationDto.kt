package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.PaymentInformationDto
import com.tanh.tourbooking.domain.model.PaymentInformation

fun PaymentInformation.toPaymentInformationDto(): PaymentInformationDto =
    PaymentInformationDto(
        accountName = accountName,
        accountNumber = accountNumber,
        amount = amount,
        bin = bin,
        checkoutUrl = checkoutUrl,
        currency = currency,
        description = description,
        expiredAt = expiredAt,
        orderCode = orderCode,
        paymentLinkId = paymentLinkId,
        qrCode = qrCode,
        status = status
    )

fun PaymentInformationDto.toPaymentInformation(): PaymentInformation =
    PaymentInformation(
        accountName = accountName,
        accountNumber = accountNumber,
        amount = amount,
        bin = bin,
        checkoutUrl = checkoutUrl,
        currency = currency,
        description = description,
        expiredAt = expiredAt,
        orderCode = orderCode,
        paymentLinkId = paymentLinkId,
        qrCode = qrCode,
        status = status
    )