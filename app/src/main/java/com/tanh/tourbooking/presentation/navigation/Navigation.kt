package com.tanh.tourbooking.presentation.navigation

import android.content.Intent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.tanh.tourbooking.presentation.bottom_bar.CustomBottomNavigationBar
import com.tanh.tourbooking.presentation.chat.ChatScreen
import com.tanh.tourbooking.presentation.booking.BookingScreen
import com.tanh.tourbooking.presentation.detail_tour.screen.DetailScreen
import com.tanh.tourbooking.presentation.explore.ExploreScreen
import com.tanh.tourbooking.presentation.failure.FailureScreen
import com.tanh.tourbooking.presentation.home.HomeScreen
import com.tanh.tourbooking.presentation.login.LoginScreen
import com.tanh.tourbooking.presentation.test.TestScreen
import com.tanh.tourbooking.presentation.message.MessageScreen
import com.tanh.tourbooking.presentation.my_tour.MyTourScreen
import com.tanh.tourbooking.presentation.profile.ProfileScreen
import com.tanh.tourbooking.presentation.register.RegisterScreen
import com.tanh.tourbooking.presentation.splashscreen.SplashScreen
import com.tanh.tourbooking.presentation.start.StartScreen
import com.tanh.tourbooking.presentation.success.SuccessScreen
import com.tanh.tourbooking.presentation.tour_list.TourListScreen
import com.tanh.tourbooking.util.Route
import com.tanh.tourbooking.util.navRoutes
import kotlinx.coroutines.launch

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    tokenViewModel: TokenViewModel = hiltViewModel<TokenViewModel>()
) {

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

    var isTokenValid by remember  {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        tokenViewModel.isTokenValid.collect { valid ->
            isTokenValid = valid
        }
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
            startDestination = if(isTokenValid) Route.HOME_SCREEN.toString() else Route.START_SCREEN.toString()
        ) {
            composable(
                route = Route.SPLASH_SCREEN.toString()
            ) {
                SplashScreen(navController = navController)
            }
            composable(
                route = Route.SUCCESS_SCREEN.toString(),
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern =
                            "https://makeitsoapp-44995.web.app/success"
                        action = Intent.ACTION_VIEW
                    }
                )
            ) {
                SuccessScreen()
            }
            composable(
                route = Route.FAILURE_SCREEN.toString(),
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern =
                            "https://makeitsoapp-44995.web.app/failure"
                        action = Intent.ACTION_VIEW
                    }
                )
            ) {
                FailureScreen()
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
                    modifier = Modifier.padding(paddingValues),
                    onNavigate = {
                        navController.navigate(it)
                    }
                ) {
                    coroutineScope.launch {
                        snackBarHosState.showSnackbar(
                            message = it,
                            withDismissAction = true,
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            }
            composable(route = Route.MY_TOURS_SCREEN.toString()) {
                MyTourScreen(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    navController.navigate(it)
                }
            }
            composable(
                route = Route.TOUR_LIST_SCREEN.toString() + "/{place}",
                arguments = listOf(
                    navArgument(name = "place") {
                        type = NavType.StringType
                    }
                )
            ) {
                TourListScreen(
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
            composable(
                route = Route.BOOKING_SCREEN.toString() + "/{state}",
                arguments = listOf(navArgument("state") {
                    type = NavType.StringType
                })
            ) {
                BookingScreen(
                    onNavigate = {
                        navController.navigate(it)
                    },
                    popBackStack = {
                        navController.popBackStack()
                    },
                    showSnackBar = {
                        coroutineScope.launch {
                            snackBarHosState.showSnackbar(
                                message = it,
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                )
            }
            composable(
                route = Route.DETAIL_SCREEN.toString() + "/{jsonTour}",
                arguments = listOf(
                    navArgument(name = "jsonTour") {
                        type = NavType.StringType
                    }
                )
            ) {
                DetailScreen(
                    modifier = Modifier.padding(paddingValues),
                    showSnackBar = {
                        coroutineScope.launch {
                            snackBarHosState.showSnackbar(
                                message = it,
                                withDismissAction = true,
                                duration = SnackbarDuration.Long
                            )
                        }
                    },
                    popBackStack = {
                        navController.popBackStack()
                    }
                ) {
                    navController.navigate(it)
                }
            }
        }
    }

}
