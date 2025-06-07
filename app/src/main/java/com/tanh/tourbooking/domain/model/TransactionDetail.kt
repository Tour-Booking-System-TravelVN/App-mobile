package com.tanh.tourbooking.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class  TransactionDetail(
    val accountName: String,
    val accountNumber: String,
    val amount: Int,
    val bin: String,
    val checkoutUrl: String,
    val currency: String,
    val description: String,
    val expiredAt: Int,
    val orderCode: Int,
    val paymentLinkId: String,
    val qrCode: String,
    val status: String
) : Parcelable