package com.tanh.tourbooking.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tanh.tourbooking.presentation.chat.ChatScreen
import com.tanh.tourbooking.presentation.message.MessageScreen
import com.tanh.tourbooking.util.Route

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.CHATS_SCREEN.toString()
    ) {
        composable(route = Route.CHATS_SCREEN.toString()) {
            ChatScreen() {
                navController.navigate(it)
            }
        }
        composable(route = Route.MESSAGE_SCREEN.toString() + "/{chatId}") {
            MessageScreen()
        }
    }

}