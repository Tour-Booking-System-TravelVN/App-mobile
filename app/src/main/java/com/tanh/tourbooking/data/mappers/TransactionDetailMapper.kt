package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.payment.TransactionDetailDto
import com.tanh.tourbooking.domain.model.TransactionDetail

fun TransactionDetailDto.toTransactionDetail(): TransactionDetail {
    return TransactionDetail(
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
}

fun TransactionDetail.toTransactionDetailDto(): TransactionDetailDto {
    return TransactionDetailDto(
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
}