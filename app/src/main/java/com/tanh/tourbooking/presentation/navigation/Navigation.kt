package com.tanh.tourbooking.presentation.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tanh.tourbooking.presentation.bottom_bar.CustomBottomNavigationBar
import com.tanh.tourbooking.presentation.chat.ChatScreen
import com.tanh.tourbooking.presentation.explore.ExploreScreen
import com.tanh.tourbooking.presentation.home.HomeScreen
import com.tanh.tourbooking.presentation.test.TestScreen
import com.tanh.tourbooking.presentation.message.MessageScreen
import com.tanh.tourbooking.presentation.profile.ProfileScreen
import com.tanh.tourbooking.util.Route
import com.tanh.tourbooking.util.navRoutes

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val coroutineScope = rememberCoroutineScope()
    val snackBarHosState = remember {
        SnackbarHostState()
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination?.route ?: "unknown"

    var showBottomBar by remember {
        mutableStateOf(currentDestination in navRoutes)
    }

    LaunchedEffect(navBackStackEntry) {
        showBottomBar = currentDestination in navRoutes
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHosState)
        },
        bottomBar = {
            if (showBottomBar) {
                CustomBottomNavigationBar(navController = navController)
            }
        },
        contentWindowInsets = WindowInsets.safeGestures
    ) { vl ->
        val paddingValues = vl
        NavHost(
            navController = navController,
            startDestination = Route.HOME_SCREEN.toString()   //Route.CHATS_SCREEN.toString()
        ) {
            composable(route = Route.CHATS_SCREEN.toString()) {
                ChatScreen(
                    modifier = Modifier.padding(paddingValues),
                    onShowSnackBar = {
                        snackBarHosState.showSnackbar(
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
                MessageScreen(
                    paddingValues = paddingValues
                ) {
                    navController.popBackStack()
                }
            }
            composable(route = "test") {
                TestScreen()
            }
            composable(route = Route.HOME_SCREEN.toString()) {
                HomeScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
            composable(route = Route.EXPLORE_SCREEN.toString()) {
                ExploreScreen()
            }
            composable(route = Route.PROFILE_SCREEN.toString()) {
                ProfileScreen()
            }

        }
    }

}