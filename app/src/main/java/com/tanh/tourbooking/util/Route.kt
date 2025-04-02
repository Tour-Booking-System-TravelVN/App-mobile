package com.tanh.tourbooking.util

enum class Route {
    HOME_SCREEN,
    CHATS_SCREEN,
    LOGIN_SCREEN,
    MESSAGE_SCREEN,
    EXPLORE_SCREEN,
    PROFILE_SCREEN,
    TOURS_SCREEN,
    DETAIL_SCREEN,
    SPLASH_SCREEN
}

val navRoutes = listOf(
    Route.HOME_SCREEN.toString(),
    Route.CHATS_SCREEN.toString(),
    Route.PROFILE_SCREEN.toString(),
    Route.EXPLORE_SCREEN.toString(),
    Route.TOURS_SCREEN.toString()
)