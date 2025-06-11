package com.tanh.tourbooking.presentation.booking.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.ui.theme.TextStyle17
import com.tanh.tourbooking.ui.theme.TextStyle20
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.Calculation

@Composable
fun BottomBar(
    totalAmount: Double,
    roomPrice: Double,
    makeAPayment: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(
                horizontal = MaterialTheme.dimens.small2,
                vertical = MaterialTheme.dimens.small1
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = "đ",
                textDecoration = TextDecoration.Underline,
                style = TextStyle20,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = Calculation.formatDouble(totalAmount + roomPrice),
                style = TextStyle20,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(Modifier.height(6.dp))
        Button(
            onClick = {
                makeAPayment()
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.small2)
                .height(45.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Thanh toán",
                style = TextStyle17,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(Modifier.height(4.dp))
    }
}