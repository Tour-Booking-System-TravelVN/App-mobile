package com.tanh.tourbooking.presentation.detail_tour.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.ui.theme.lightGray
import com.tanh.tourbooking.util.Calculation

@Composable
fun BookingBarSection(
    modifier: Modifier = Modifier,
    amount: Double,
    bookTour: () -> Unit
) {

    val screenWithDp = LocalConfiguration.current.screenWidthDp.dp

    Column (
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        HorizontalDivider(
            Modifier.fillMaxWidth(),
            1.dp,
            lightGray
        )
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        Row(
            modifier = Modifier.padding(
                vertical = MaterialTheme.dimens.small1,
                horizontal = MaterialTheme.dimens.small2
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "đ",
                style = MaterialTheme.typography.titleLarge,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = Calculation.formatDouble(amount),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {
                    bookTour()
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(screenWithDp / 2 - 20.dp).aspectRatio(4f)
            ) {
                Text(
                    text = "Đặt ngay",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

}