package com.tanh.tourbooking.presentation.message

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tanh.tourbooking.data.mappers.TimeFormatter
import com.tanh.tourbooking.domain.model.Message
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import com.tanh.tourbooking.ui.theme.dimens

@Suppress("DEPRECATION")
@Composable
fun MessageScreen(
    modifier: Modifier = Modifier,
    viewModel: MessageViewModel = hiltViewModel<MessageViewModel>(),
    paddingValues: PaddingValues,
    onNavigate: (String) -> Unit,
    onPopBackStack: () -> Unit = {}
) {

    val clipboardManager = LocalClipboardManager.current

    val state = viewModel.state.collectAsState().value
    var inputMessage by remember {
        mutableStateOf("")
    }

    var isError by remember {
        mutableStateOf(state.error != null)
    }

    var currentMessage by remember {
        mutableStateOf<Message?>(null)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is OneTimeEvent.Navigate -> onNavigate(event.route)
                OneTimeEvent.PopBackStack -> onPopBackStack()
                is OneTimeEvent.ShowSnackbar -> Unit
                is OneTimeEvent.ShowToast -> Unit
                is OneTimeEvent.OpenLink -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        currentMessage = null
                    }
                )
            }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        viewModel.onPopBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = state.chatbox?.name ?: "No name",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
//            if(state.role == Role.TOURGUIDE) {
                IconButton(
                    onClick = {
                        viewModel.onNavToWaitingId()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
//            }
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.Gray
            )
            if (!state.isLoading) {
                LazyColumn(
                    reverseLayout = true,
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val groupedMessages = state.messages.groupBy { it.time.toLocalDate() }
                    groupedMessages.forEach { (date, list) ->

                        items(list) { message ->
                            MessageItem(
                                message = message,
                                userId = state.userId ?: 0,
                                modifier = Modifier.pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {
                                            currentMessage = message
                                        }
                                    )
                                }
                            )
                        }
                        item {
                            if (list.isNotEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .padding(horizontal = 8.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = TimeFormatter.formatDateAndYear(date),
                                        fontSize = 8.sp,
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = inputMessage,
                    onValueChange = {
                        inputMessage = it
                    },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant
                    ), trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.sendMessage(inputMessage)
                                inputMessage = ""
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
            }

        }
        androidx.compose.animation.AnimatedVisibility(
            visible = currentMessage != null,
            enter = slideInVertically { it },
            exit = slideOutVertically { it },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(MaterialTheme.dimens.small2),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier.clickable {
                        clipboardManager.setText(AnnotatedString(currentMessage?.text ?: ""))
                    },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(25.dp)
                            .alpha(0.3f)
                    )
                    Text(
                        text = "Sao chép",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Column(
                    modifier = Modifier.clickable {
                        viewModel.recallMessage(currentMessage!!)
                        currentMessage = null
                    },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                    )
                    Text(
                        text = "Xóa",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }


                Column(
                    modifier = Modifier.clickable {
                        currentMessage = null
                    },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(25.dp)
                            .alpha(0.3f)
                    )
                    Text(
                        text = "Hủy",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewMessageScreen(modifier: Modifier = Modifier) {
    TourBookingTheme {
//        MessageScreen(paddingValues = PaddingValues(6.dp))
    }
}