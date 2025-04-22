package com.tanh.tourbooking.presentation.booking.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.R
import com.tanh.tourbooking.presentation.booking.BookingUiState
import com.tanh.tourbooking.ui.theme.TextStyle17
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.ui.theme.lightGray

@Composable
fun RestSection(
    checked: Boolean,
    onCheckChange: () -> Unit,
    modifier: Modifier = Modifier,
    state: BookingUiState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.small2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = modifier
                    .height(16.dp)
                    .width(5.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(Modifier.width(MaterialTheme.dimens.small2))
            Text(text = "Giảm giá", style = TextStyle17)
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small3))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.small2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mã ưu đãi",
                style = TextStyle17
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = state.state.discount?.discountName ?: "Không khả dụng",
                style = TextStyle17,
                color = lightGray
            )
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(MaterialTheme.dimens.small2)
        ) {
            Row(
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        onCheckChange()
                    }
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Tôi đã hiểu và đồng ý với Điều khoản sử dụng chung và Chính sách Quyền riêng tư của TravelVN",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(Modifier.height(MaterialTheme.dimens.small1))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        1.dp,
                        Color(0xFFc4deda),
                        MaterialTheme.shapes.medium
                    )
                    .background(Color(0xFFeef9f5))
                    .padding(
                        horizontal = MaterialTheme.dimens.small2,
                        vertical = MaterialTheme.dimens.small1
                    )
            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.doccheck),
                        contentDescription = null,
                        tint = Color(0xFF227576),
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Hủy miễn phí 24 giờ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF227576)
                    )
                }
            }
            Spacer(Modifier.height(MaterialTheme.dimens.small1))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        1.dp,
                        Color(0xFFefdfb6),
                        MaterialTheme.shapes.medium
                    )
                    .background(Color(0xFFfcf2e1))
                    .padding(
                        horizontal = MaterialTheme.dimens.small2,
                        vertical = MaterialTheme.dimens.small1
                    )
            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Xin điền thông tin cẩn thận. Khi gửi sẽ không thay đổi",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }
            }
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
        }
    }

}