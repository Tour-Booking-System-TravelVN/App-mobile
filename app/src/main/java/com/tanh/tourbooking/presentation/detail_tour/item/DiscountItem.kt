package com.tanh.tourbooking.presentation.detail_tour.item

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.ui.theme.TourBookingTheme

@Composable
fun DiscountItem(
    modifier: Modifier = Modifier,
    discountName: String
) {
    Row(
        modifier = Modifier
            .padding(0.dp)
            .border(
                1.dp,
                Color(0xFFa57275),
                RectangleShape
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Discount,
            contentDescription = null,
            tint = Color(0xFFF44020)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = discountName,
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFF9b6268)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewDiscountItem(modifier: Modifier = Modifier) {
    TourBookingTheme {
        DiscountItem(discountName = "Giảm 45%")
    }
}