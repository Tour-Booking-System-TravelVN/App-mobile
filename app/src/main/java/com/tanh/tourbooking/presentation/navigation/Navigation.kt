package com.tanh.tourbooking.presentation.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.tanh.tourbooking.presentation.bottom_bar.CustomBottomNavigationBar
import com.tanh.tourbooking.presentation.chat.ChatScreen
import com.tanh.tourbooking.presentation.booking.BookingScreen
import com.tanh.tourbooking.presentation.booking.BookingViewModel
import com.tanh.tourbooking.presentation.category.CategoryScreen
import com.tanh.tourbooking.presentation.detail_tour.screen.DetailScreen
import com.tanh.tourbooking.presentation.explore.ExploreScreen
import com.tanh.tourbooking.presentation.explore.ExploreViewModel
import com.tanh.tourbooking.presentation.failure.FailureScreen
import com.tanh.tourbooking.presentation.home.HomeScreen
import com.tanh.tourbooking.presentation.login.LoginScreen
import com.tanh.tourbooking.presentation.test.TestScreen
import com.tanh.tourbooking.presentation.message.MessageScreen
import com.tanh.tourbooking.presentation.message.waiting_screen.WaitingScreen
import com.tanh.tourbooking.presentation.my_tour.MyTourViewModel
import com.tanh.tourbooking.presentation.my_tour.detail_screen.DetailMyTourScreen
import com.tanh.tourbooking.presentation.my_tour.main_screen.MyTourScreen
import com.tanh.tourbooking.presentation.profile.overall.ProfileScreen
import com.tanh.tourbooking.presentation.profile.ProfileViewModel
import com.tanh.tourbooking.presentation.profile.information.InformationScreen
import com.tanh.tourbooking.presentation.profile.password.PasswordScreen
import com.tanh.tourbooking.presentation.register.RegisterScreen
import com.tanh.tourbooking.presentation.splashscreen.SplashScreen
import com.tanh.tourbooking.presentation.start.StartScreen
import com.tanh.tourbooking.presentation.success.SuccessScreen
import com.tanh.tourbooking.presentation.tour_list.TourListScreen
import com.tanh.tourbooking.util.Role
import com.tanh.tourbooking.util.Route
import com.tanh.tourbooking.util.navRoutes
import com.tanh.tourbooking.util.sharedViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState", "UnrememberedGetBackStackEntry")
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

    var isTokenValid by remember {
        mutableStateOf(false)
    }

    var openFromDeepLink by remember {
        mutableStateOf(false)
    }

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(Unit) {
        tokenViewModel.role.collect { newRole ->
            if(newRole == Role.TOURGUIDE) {
                navController.navigate(Route.CHATS_SCREEN.toString())
            }
        }

    }

    LaunchedEffect(navBackStackEntry) {
        showBottomBar = currentDestination in navRoutes

        when (navBackStackEntry?.destination?.route) {
            Route.HOME_SCREEN.toString() -> selectedIndex = 0
            Route.EXPLORE_SCREEN.toString() -> selectedIndex = 1
            Route.MY_TOURS_SCREEN.toString() -> selectedIndex = 2
            Route.CHATS_SCREEN.toString() -> selectedIndex = 3
            Route.PROFILE_SCREEN.toString() -> selectedIndex = 4
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHosState)
        },
        bottomBar = {
            if (showBottomBar) {
                CustomBottomNavigationBar(
                    navController = navController,
                    selectedIndex = selectedIndex,
                    viewModel = tokenViewModel,
                    onIndexChange = { currentIndex ->
                        selectedIndex = currentIndex
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets.safeGestures
    ) { vl ->
        val paddingValues = vl
        LaunchedEffect(Unit) {
            tokenViewModel.getInfo()
            tokenViewModel.isTokenValid.collect { valid ->
                isTokenValid = valid
            }
        }
        NavHost(
            navController = navController,
            startDestination = Route.LAUNCHER_SCREEN.toString()
        ) {
            //bottom graph
            //bottom nav
            navigation(
                startDestination = Route.HOME_SCREEN.toString(),
                route = "bottom_graph"
            ) {

                //home
                composable(route = Route.HOME_SCREEN.toString()) {
                    HomeScreen(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        navController.navigate(it) {
                            launchSingleTop = true
                        }
                    }
                }

                //explore
                composable(route = Route.EXPLORE_SCREEN.toString()) { entry ->
                    val parentEntry = remember { navController.getBackStackEntry("bottom_graph") }
                    val viewModel = hiltViewModel<ExploreViewModel>(entry)

                    ExploreScreen(
                        modifier = Modifier.padding(paddingValues),
                        viewModel = viewModel,
                        onNavigate = {
                            navController.navigate(it) {
                                launchSingleTop = true
                            }
                        },
                        showSnackbar = {
                            coroutineScope.launch {
                                snackBarHosState.showSnackbar(
                                    message = it,
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Long
                                )
                            }
                        }
                    )
                }

                //my tour graph
                navigation(
                    startDestination = Route.MY_TOURS_SCREEN.toString(),
                    route = "mytours"
                ) {

                    composable(Route.MY_TOURS_SCREEN.toString()) { entry ->
                        val viewModel = entry.sharedViewModel<MyTourViewModel>(navController)

                        MyTourScreen(
                            viewModel = viewModel,
                            onNavigate = {
                                navController.navigate(it) {
                                    launchSingleTop = true
                                }
                            },
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            coroutineScope.launch {
                                snackBarHosState.showSnackbar(
                                    message = it,
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }

                    composable(Route.DETAIL_MYTOUR_SCREEN.toString()) { entry ->
                        val viewModel = entry.sharedViewModel<MyTourViewModel>(navController)

                        DetailMyTourScreen(
                            viewModel = viewModel,
                            onNavigate = {
                                navController.navigate(it) {
                                    launchSingleTop = true
                                }
                            },
                            popBackStack = {
                                navController.popBackStack()
                            }
                        ) {
                            coroutineScope.launch {
                                snackBarHosState.showSnackbar(
                                    message = it,
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }
                }

                //chats
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
                        navController.navigate(it) {
                            popUpTo(Route.CHGPWD_SCREEN.toString()) {
                                inclusive = true

                            }
                            launchSingleTop = true
                        }
                    }
                }

                //profile graph
                navigation(
                    startDestination = Route.PROFILE_SCREEN.toString(),
                    route = "profile"
                ) {
                    composable(Route.PROFILE_SCREEN.toString()) { entry ->
                        val viewModel = entry.sharedViewModel<ProfileViewModel>(navController)

                        ProfileScreen(
                            modifier = Modifier.padding(paddingValues),
                            viewModel = viewModel,
                            onNavigate = {route ->
                                if(route == Route.LOGIN_SCREEN.toString()) {
                                    navController.navigate(route) {
                                        popUpTo("bottom_graph") {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                } else {
                                    navController.navigate(route)
                                }
                            },
                            popBackStack = {
                                navController.popBackStack()
                            },
                            showSnackBar = {
                                coroutineScope.launch {
                                    snackBarHosState.showSnackbar(
                                        message = it,
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Long
                                    )
                                }
                            }
                        )
                    }

                    composable(Route.INFOR_SCREEN.toString()) { entry ->
                        val viewModel = entry.sharedViewModel<ProfileViewModel>(navController)

                        InformationScreen(
                            viewModel = viewModel,
                            onNavigate = {
                                navController.navigate(it) {
                                    launchSingleTop = true
                                }
                            },
                            popBackStack = {
                                navController.popBackStack()
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

                    composable(Route.CHGPWD_SCREEN.toString()) { entry ->
                        val viewModel = entry.sharedViewModel<ProfileViewModel>(navController)

                        PasswordScreen(
                            viewModel = viewModel
                        ) {
                            navController.popBackStack()
                        }
                    }
                }

            }

            //splash
            composable(
                route = Route.LAUNCHER_SCREEN.toString()
            ) {
                openFromDeepLink = false

                var dest by remember {
                    mutableStateOf("")
                }

                LaunchedEffect(isTokenValid) {
                    dest = if (isTokenValid) Route.HOME_SCREEN.toString()
                    else Route.START_SCREEN.toString()
                }
                SplashScreen {
                    if (dest.isNotBlank()) {
                        navController.navigate(dest) {
                            popUpTo(Route.LAUNCHER_SCREEN.toString()) {
                                inclusive = true
                            }
                        }
                    }
                }
            }

            //success payment
            composable(
                route = Route.SUCCESS_SCREEN.toString() + "?orderCode={orderCode}",
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern =
                            "makeitsoapp://success?orderCode={orderCode}"
                        action = Intent.ACTION_VIEW
                    }
                ),
                arguments = listOf(
                    navArgument(
                        name = "orderCode"
                    ) {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                openFromDeepLink = true
                val orderCode = entry.arguments?.getString("orderCode")
                SuccessScreen(
                    orderCode = orderCode
                ) {
                    navController.navigate(it) {
                        popUpTo(Route.SUCCESS_SCREEN.toString()) {
                            inclusive = true
                        }
                    }
                }
            }

            //failure payment
            composable(
                route = Route.FAILURE_SCREEN.toString(),
                deepLinks = listOf(
                    navDeepLink {
                        openFromDeepLink = true
                        uriPattern =
                            "makeitsoapp://failure"
                        action = Intent.ACTION_VIEW
                    }
                )
            ) {
                openFromDeepLink = true
                FailureScreen(navController = navController)
            }

            //message
            composable(route = Route.MESSAGE_SCREEN.toString() + "/{chatId}") {
                MessageScreen(
                    paddingValues = paddingValues,
                    onNavigate = {
                        navController.navigate(it) {
                            launchSingleTop = true
                        }
                    }
                ) {
                    navController.popBackStack()
                }
            }

            //waiting
            composable(
                route = Route.WAITING_SCREEN.toString() + "/{chatId}",
                arguments = listOf(
                    navArgument(name = "chatId") {
                        type = NavType.StringType
                    }
                )
            ) {
                WaitingScreen {
                    navController.popBackStack()
                }
            }
            composable(route = "test") {
                TestScreen()
            }

            //tourlist
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
                    navController.navigate(it) {
                        launchSingleTop = true
                    }
                }
            }
            composable(
                route = Route.CATEGORY_SCREEN.toString() + "/{categoryName}",
                arguments = listOf(
                    navArgument(name = "categoryName") {
                        type = NavType.StringType
                    }
                )
            ) {
                CategoryScreen(
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
                    navController.navigate(it) {
                        launchSingleTop = true
                    }
                }
            }
            composable(route = Route.START_SCREEN.toString()) {
                StartScreen() {
                    navController.navigate(it)
                }
            }
            composable(route = Route.LOGIN_SCREEN.toString()) {
                tokenViewModel.getInfo()
                
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
                    navController.navigate(it) {
                        popUpTo(Route.LOGIN_SCREEN.toString()) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
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
                    navController.navigate(it) {
                        popUpTo(Route.REGISTER_SCREEN.toString()) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
            composable(
                route = Route.BOOKING_SCREEN.toString() + "/{state}",
                arguments = listOf(navArgument("state") {
                    type = NavType.StringType
                })
            ) { entry ->
                BookingScreen(
                    onNavigate = {
                        navController.navigate(it) {
                            launchSingleTop = true
                        }
                    },
                    popBackStack = {
                        navController.popBackStack()
                    },
                    viewModel = hiltViewModel<BookingViewModel>(entry),
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
                    navController.navigate(it) {
                        launchSingleTop = true
                    }
                }
            }
        }
    }

}
