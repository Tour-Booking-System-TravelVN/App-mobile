package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.payment.ConfirmedDataDto
import com.tanh.tourbooking.domain.model.ConfirmedData

fun ConfirmedDataDto.toConfirmedData(): ConfirmedData {
    return ConfirmedData(
        amount = this.amount,
        amountPaid = this.amountPaid,
        amountRemaining = this.amountRemaining,
        canceledAt = this.canceledAt,
        cancellationReason = this.cancellationReason,
        createdAt = this.createdAt,
        id = this.id,
        orderCode = this.orderCode,
        status = this.status,
        transactions = this.transactions
    )
}

fun ConfirmedData.toConfirmedDataDto(): ConfirmedDataDto {
    return ConfirmedDataDto(
        amount = this.amount,
        amountPaid = this.amountPaid,
        amountRemaining = this.amountRemaining,
        canceledAt = this.canceledAt,
        cancellationReason = this.cancellationReason,
        createdAt = this.createdAt,
        id = this.id,
        orderCode = this.orderCode,
        status = this.status,
        transactions = this.transactions
    )
}