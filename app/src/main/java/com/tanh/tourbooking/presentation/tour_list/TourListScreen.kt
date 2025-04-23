package com.tanh.tourbooking.presentation.tour_list

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.presentation.util.multipleEventsCutter
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.Route

@Composable
fun TourListScreen(
    modifier: Modifier = Modifier,
    viewModel: TourListViewModel = hiltViewModel<TourListViewModel>(),
    showSnackBar: (String) -> Unit,
    onNavigate: (String) -> Unit
) {

    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is OneTimeEvent.Navigate -> onNavigate(event.route)
                OneTimeEvent.PopBackStack -> Unit
                is OneTimeEvent.ShowSnackbar -> showSnackBar(event.message)
                is OneTimeEvent.ShowToast -> Unit
                is OneTimeEvent.OpenLink -> Unit
            }
        }
    }

    if(state.isLoading) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    vertical = MaterialTheme.dimens.small1,
                    horizontal = MaterialTheme.dimens.small1
                )
        ) {
            Text(
                text = "${viewModel._place.value}, Việt Nam",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            Text(
                text = "Có ${state.list.size} tour ở ${viewModel._place.value}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))
            LazyColumn(
                contentPadding = PaddingValues(MaterialTheme.dimens.small1),
                modifier = Modifier.weight(1f)
            ) {
                items(state.list) { tour ->
                    multipleEventsCutter { eventManager ->
                        TourUnitItem(
                            tour = tour,
                            modifier = Modifier.padding(bottom = MaterialTheme.dimens.small2).clickable {
                                eventManager.processEvent {
                                    Log.d("CLICK", "Click1")
                                    viewModel.onEvent(TourListEvent.OnClickTour(tour.tourUnitId))
                                }
                            }
                        )
                    }
                }
            }
        }
    }

}