package com.tanh.tourbooking.presentation.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
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
import com.tanh.tourbooking.presentation.detail_tour.DetailScreen
import com.tanh.tourbooking.presentation.explore.ExploreScreen
import com.tanh.tourbooking.presentation.home.HomeScreen
import com.tanh.tourbooking.presentation.login.LoginScreen
import com.tanh.tourbooking.presentation.test.TestScreen
import com.tanh.tourbooking.presentation.message.MessageScreen
import com.tanh.tourbooking.presentation.my_tour.MyTourScreen
import com.tanh.tourbooking.presentation.profile.ProfileScreen
import com.tanh.tourbooking.presentation.register.RegisterScreen
import com.tanh.tourbooking.presentation.splashscreen.SplashScreen
import com.tanh.tourbooking.presentation.start.StartScreen
import com.tanh.tourbooking.presentation.tour_list.TourListScreen
import com.tanh.tourbooking.util.Route
import com.tanh.tourbooking.util.navRoutes
import kotlinx.coroutines.launch

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
            startDestination = Route.START_SCREEN.toString()   //Route.CHATS_SCREEN.toString()
        ) {
            composable(route = Route.SPLASH_SCREEN.toString()) {
                SplashScreen(navController = navController)
            }
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
                ) {
                    navController.navigate(it)
                }
            }
            composable(route = Route.EXPLORE_SCREEN.toString()) {
                ExploreScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
            composable(route = Route.PROFILE_SCREEN.toString()) {
                ProfileScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
            composable(route = Route.TOURS_SCREEN.toString()) {
                MyTourScreen(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    navController.navigate(it)
                }
            }
            composable(route = Route.DETAIL_SCREEN.toString()) {
                DetailScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
            composable(route = Route.TOUR_LIST_SCREEN.toString()) {
                TourListScreen() {
                    navController.navigate(it)
                }
            }
            composable(route = Route.START_SCREEN.toString()) {
                StartScreen() {
                    navController.navigate(it)
                }
            }
            composable(route = Route.LOGIN_SCREEN.toString()) {
                LoginScreen(
                    showSnackBar = {
                        coroutineScope.launch {
                            snackBarHosState.showSnackbar(
                                message = it,
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                ) {
                    navController.navigate(it)
                }
            }
            composable(route = Route.REGISTER_SCREEN.toString()) {
                RegisterScreen(
                    showSnackbar = {
                        coroutineScope.launch {
                            snackBarHosState.showSnackbar(
                                message = it,
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                ) {
                    navController.navigate(it)
                }
            }
        }
    }

}
