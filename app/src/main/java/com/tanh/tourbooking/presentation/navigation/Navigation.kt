package com.tanh.tourbooking.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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

    val coroutineScope = rememberCoroutineScope()
    val snackBarHosState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHosState)
        }
    ) { vl ->
        val paddingValues = vl
        NavHost(
            navController = navController,
            startDestination = Route.CHATS_SCREEN.toString()
        ) {
            composable(route = Route.CHATS_SCREEN.toString()) {
                ChatScreen(
                    onShowSnackBar = {
                        snackBarHosState.
                        showSnackbar(
                            message = it,
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                    }
                ) {
                    navController.navigate(it)
                }
            }
            composable(route = Route.MESSAGE_SCREEN.toString() + "/{chatId}") {
                MessageScreen() {
                    navController.popBackStack()
                }
            }
        }
    }

}