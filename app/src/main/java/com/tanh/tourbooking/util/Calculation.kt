package com.tanh.tourbooking.util

import android.annotation.SuppressLint
import com.tanh.tourbooking.domain.model.Discount
import com.tanh.tourbooking.domain.model.Rating
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

object Calculation {

    fun averageRatings(ratings: List<Rating>): Double {
        if(ratings.isEmpty()) return 0.0
        val total = ratings.sumOf { it.ratingValue }
        val average = total.toDouble() / ratings.size
        val roundedRating = (average * 2).roundToInt() / 2.0
        return String.format(Locale.US ,"%.1f", roundedRating).toDouble()
    }

    fun formatDuration(duration: String): String {
        if(duration.length != 4) return ""
        val formattedDuration = "${duration[0]} ngày, ${duration[2]} đêm"
        return formattedDuration
    }

    fun formatDouble(amount: Double): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        return formatter.format(amount)
    }

    fun discountedPrice(amount: Double, discount: Discount?): Double {
        if(discount == null) return amount
        return when (discount.discountUnit) {
            "%" -> {
                amount * ((100 - discount.discountValue) / 100)
            }
            "VND" -> {
                val value = discount.discountValue
                amount - value
            }
            else -> {
                0.0
            }
        }
    }

}