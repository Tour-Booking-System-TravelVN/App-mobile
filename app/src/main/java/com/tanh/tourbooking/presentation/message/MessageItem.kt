package com.tanh.tourbooking.presentation.message

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tanh.tourbooking.data.model.mappers.TimeFormatter
import com.tanh.tourbooking.domain.model.Message
import com.tanh.tourbooking.ui.theme.TourBookingTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageItem(
    message: Message,
    userId: Int,
    modifier: Modifier = Modifier
) {

    val widthScreen = LocalConfiguration.current.screenWidthDp.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        if (message.senderId == userId) {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .widthIn(max = widthScreen / 2)
                    .clip(
                        RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 0.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    )
                    .border(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 0.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(6.dp)
            ) {
                Text(
                    text = message.text,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 14.sp,
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = TimeFormatter.formatTime(message.time),
                    fontSize = 8.sp,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .widthIn(max = widthScreen / 2)
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    )
                    .border(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(6.dp)
            ) {
                Text(
                    text = message.senderName,
                    fontSize = 12.sp,
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = message.text,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 14.sp,
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = TimeFormatter.formatTime(message.time),
                    fontSize = 8.sp,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MessageItemPreivew(modifier: Modifier = Modifier) {
    TourBookingTheme {
        MessageItem(
            message = Message(
                senderId = 1,
                text = "Hello",
                time = java.time.LocalDateTime.now()
            ),
            userId = 1
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MessageItemPreivew2(modifier: Modifier = Modifier) {
    TourBookingTheme {
        MessageItem(
            message = Message(
                senderId = 1,
                text = "Hello",
                time = java.time.LocalDateTime.now()
            ),
            userId = 2
        )
    }
}