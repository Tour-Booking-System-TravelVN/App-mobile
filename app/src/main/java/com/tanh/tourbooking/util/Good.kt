package com.tanh.tourbooking.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import com.tanh.tourbooking.presentation.bottom_bar.NavigationBarItemData

object Good {

    val listBottomBar = listOf(
        NavigationBarItemData(
            title = "Home",
            route = Route.HOME_SCREEN.toString(),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
        ),
        NavigationBarItemData(
            title = "Explore",
            route = Route.EXPLORE_SCREEN.toString(),
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
        ),
        NavigationBarItemData(
            title = "Tours",
            route = Route.TOURS_SCREEN.toString(),
            selectedIcon = Icons.Filled.Place,
            unselectedIcon = Icons.Outlined.Place,
        ),
        NavigationBarItemData(
            title = "Message",
            route = Route.CHATS_SCREEN.toString(),
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.MailOutline,
        ),
        NavigationBarItemData(
            title = "Profile",
            route = Route.PROFILE_SCREEN.toString(),
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
        )
    )

}