package com.tanh.tourbooking.data.model.dto.payment

data class ConfirmedDataDto(
    val amount: Int,
    val amountPaid: Int,
    val amountRemaining: Int,
    val canceledAt: String?,
    val cancellationReason: String?,
    val createdAt: String?,
    val id: String,
    val orderCode: Int,
    val status: String,
    val transactions: List<Any>?
)