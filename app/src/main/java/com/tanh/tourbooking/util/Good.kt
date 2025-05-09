package com.tanh.tourbooking.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Timelapse
import com.tanh.tourbooking.presentation.bottom_bar.NavigationBarItemData

object Good {

    val listBottomBar = listOf(
        NavigationBarItemData(
            title = "Trang chủ",
            route = Route.HOME_SCREEN.toString(),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
        ),
        NavigationBarItemData(
            title = "Tìm kiếm",
            route = Route.EXPLORE_SCREEN.toString(),
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
        ),
        NavigationBarItemData(
            title = "Hoạt động",
            route = Route.MY_TOURS_SCREEN.toString(),
            selectedIcon = Icons.Filled.Timelapse,
            unselectedIcon = Icons.Outlined.Timelapse,
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