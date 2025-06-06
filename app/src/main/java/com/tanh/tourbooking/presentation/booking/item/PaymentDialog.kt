package com.tanh.tourbooking.presentation.booking.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.DialogHost
import com.tanh.tourbooking.R
import com.tanh.tourbooking.ui.theme.TourBookingTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PaymentDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onUrlClick: () -> Unit,
    onZaloClick: () -> Unit
) {

    var currentCheckBox by remember {
        mutableIntStateOf(1)
    }

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        Column(
            modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Chọn phương thức thanh toán",
                style = MaterialTheme.typography.bodyLargeEmphasized
            )
            Spacer(Modifier.height(12.dp))
            Box(
                Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium
                    )
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.qr),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Thanh toán qua QR",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.weight(1f))
                    RadioButton(
                        selected = currentCheckBox == 1,
                        onClick = {
                            currentCheckBox = 1
                        }
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Box(
                Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.secondary,
                        shape = MaterialTheme.shapes.medium
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.bank),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Thanh toán qua ZaloPay",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.weight(1f))
                    RadioButton(
                        selected = currentCheckBox == 2,
                        onClick = {
                            currentCheckBox = 2
                        }
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (currentCheckBox == 1) {
                            onUrlClick()
                        } else {
                            onZaloClick()
                        }
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Xác nhận")
                }
                Spacer(Modifier.width(8.dp))
                OutlinedButton(
                    onClick = {
                        onDismissRequest()
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Hủy")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewPaymentDialog(
    modifier: Modifier = Modifier
) {
    TourBookingTheme {
        PaymentDialog(onDismissRequest = {}, onUrlClick = {}) { }
    }
}