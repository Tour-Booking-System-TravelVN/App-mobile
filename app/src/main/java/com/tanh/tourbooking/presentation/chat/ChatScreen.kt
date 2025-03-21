package com.tanh.tourbooking.presentation.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.tanh.tourbooking.presentation.util.OneTimeEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    viewModel: ChatsViewModel = hiltViewModel<ChatsViewModel>(),
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {

    val state = viewModel.chat.collectAsState(initial = emptyList()).value

    LaunchedEffect(key1 = Unit) {
        viewModel.channel.collect { event ->
            when(event) {
                is OneTimeEvent.Navigate -> {onNavigate(event.route)}
                OneTimeEvent.PopBackStack -> Unit
                is OneTimeEvent.ShowSnackbar -> Unit
                is OneTimeEvent.ShowToast -> Unit
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(state) { chatbox ->
            ChatItem(chatBox = chatbox) {
                viewModel.onNavToMessage(chatbox.chatId)
            }
        }
    }

}