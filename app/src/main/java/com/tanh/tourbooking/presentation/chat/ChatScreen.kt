package com.tanh.tourbooking.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.dimens


@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatsViewModel = hiltViewModel<ChatsViewModel>(),
    onShowSnackBar: suspend (String) -> Unit,
    onNavigate: (String) -> Unit
) {

    val chat = viewModel.chat.collectAsState(initial = emptyList()).value
    val waitedChat = viewModel.waitedChats.collectAsState(initial = emptyList()).value

    var inputBookingId by remember {
        mutableStateOf("")
    }
    var showDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is OneTimeEvent.Navigate -> {
                    onNavigate(event.route)
                }

                OneTimeEvent.PopBackStack -> Unit
                is OneTimeEvent.ShowSnackbar -> {
                    onShowSnackBar(event.message)
                }
                is OneTimeEvent.OpenLink -> Unit
                is OneTimeEvent.ShowToast -> Unit
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = !showDialog
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        contentWindowInsets = WindowInsets.safeGestures,
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(paddingValues)
        ) {
            Column {
                Text(
                    text = "Tin nhắn",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    shadowElevation = 4.dp
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        itemsIndexed(chat) { index, chatbox ->
                            Column {
                                ChatItem(
                                    chatBox = chatbox,
                                    status = false
                                ) {
                                    viewModel.onNavToMessage(chatbox.chatId)
                                }
                                if (index < chat.size - 1) {
                                    HorizontalDivider(
                                        color = Color.Gray,
                                        thickness = 0.5.dp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                    )
                                }
                            }
                        }
                        itemsIndexed(waitedChat) { index, chatbox ->
                            Column {
                                ChatItem(
                                    chatBox = chatbox,
                                    status = true
                                ) {
                                }
                                if (index < chat.size - 1) {
                                    HorizontalDivider(
                                        color = Color.Gray,
                                        thickness = 0.5.dp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        if(showDialog) {
            Dialog(
                onDismissRequest = {
                    showDialog = false
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = inputBookingId,
                        onValueChange = {
                            inputBookingId = it
                        }
                    )
                    Button(
                        onClick = {
                            viewModel.validChatBookingId(inputBookingId)
                            inputBookingId = ""
                            showDialog = false
                        }
                    ) {
                        Text("Tham gia")
                    }
                }
            }
        }
    }

}