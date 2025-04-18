package com.tanh.tourbooking.presentation.detail_tour.item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.ui.theme.starColor

@Composable
fun RatingBarDisplay(
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
    averageRating: Double
) {

    val fullStars = averageRating.toInt()
    val hasHalfStars = (averageRating - fullStars) >= 0.5
    val emptyStars = 5 - fullStars - if(hasHalfStars) 1 else 0
    Row(
        Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(fullStars) {
            Icon(
                imageVector = Icons.Filled.StarRate,
                contentDescription = "fullstar",
                tint = starColor,
                modifier = Modifier.size(size)
            )
        }
        if(hasHalfStars) {
            Icon(
                imageVector = Icons.Filled.StarHalf,
                contentDescription = "starhalf",
                tint = starColor,
                modifier = Modifier.size(size)
            )
        }
        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Outlined.StarBorder,
                contentDescription = "starborder",
                tint = starColor,
                modifier = Modifier.size(size)
            )
        }
    }

}