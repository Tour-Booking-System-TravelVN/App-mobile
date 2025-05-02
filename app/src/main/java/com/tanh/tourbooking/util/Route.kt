package com.tanh.tourbooking.util

enum class Route {
    HOME_SCREEN,
    CHATS_SCREEN,
    LOGIN_SCREEN,
    MESSAGE_SCREEN,
    EXPLORE_SCREEN,
    PROFILE_SCREEN,
    MY_TOURS_SCREEN,
    DETAIL_SCREEN,
    SPLASH_SCREEN,
    TOUR_LIST_SCREEN,
    START_SCREEN,
    REGISTER_SCREEN,
    BOOKING_SCREEN,
    PAGER_SCREEN,
    SUCCESS_SCREEN,
    FAILURE_SCREEN,
    WAITING_SCREEN,
    DETAIL_MYTOUR_SCREEN
}

val navRoutes = listOf(
    Route.PAGER_SCREEN.toString(),
    Route.HOME_SCREEN.toString(),
    Route.CHATS_SCREEN.toString(),
    Route.PROFILE_SCREEN.toString(),
    Route.EXPLORE_SCREEN.toString(),
    Route.MY_TOURS_SCREEN.toString(),
)