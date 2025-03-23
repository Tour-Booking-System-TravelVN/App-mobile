package com.tanh.tourbooking.presentation.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tanh.tourbooking.R
import com.tanh.tourbooking.data.model.mappers.TimeFormatter
import com.tanh.tourbooking.domain.model.ChatBox
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatItem(
    chatBox: ChatBox,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {

    Row(
        modifier = modifier.fillMaxWidth().clickable {
            onClick(chatBox.chatId)
        }.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.group),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onTertiary)

        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = chatBox.name,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = chatBox.message,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = TimeFormatter.formatDate(chatBox.lastTime),
            style = MaterialTheme.typography.titleSmall,
            fontSize = 12.sp
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun ChatItemPreivew(modifier: Modifier = Modifier) {
    TourBookingTheme {
        ChatItem(
            chatBox = ChatBox(
                participants = emptyList(),
                lastTime = LocalDateTime.now(),
                message = "Hello",
                adminId = 1,
                chatId = "awjdakwdjkawd",
                name = "Nhóm đi ninh bình"
            )
        ) {}
    }
}