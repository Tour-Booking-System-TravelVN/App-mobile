package com.tanh.tourbooking.presentation.detail_tour.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.tanh.tourbooking.presentation.detail_tour.BookingTourState
import com.tanh.tourbooking.presentation.detail_tour.DetailViewModel

@Composable
fun BookingScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel<DetailViewModel>()
) {

    val state = viewModel.booking.collectAsState(initial = BookingTourState()).value
    

}