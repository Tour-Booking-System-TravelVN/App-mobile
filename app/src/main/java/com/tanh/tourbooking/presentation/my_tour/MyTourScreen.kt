package com.tanh.tourbooking.presentation.my_tour

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.FakeData
import com.tanh.tourbooking.util.Route
import com.tanh.tourbooking.util.TourStatus

@Composable
fun MyTourScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {

    var currentStatus by remember { mutableStateOf("") }
    val isFiltered = currentStatus.isNotBlank()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                start = MaterialTheme.dimens.small2,
                end = MaterialTheme.dimens.small2,
                top = MaterialTheme.dimens.small3
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val buttons = listOf(
                "Upcoming" to TourStatus.UPCOMING.toString(),
                "Ongoing" to TourStatus.ONGOING.toString(),
                "Completed" to TourStatus.COMPLETED.toString()
            )

            buttons.forEach { (label, status) ->
                Button(
                    onClick = {
                        currentStatus = if (currentStatus == status) "" else status
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentStatus == status)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.primaryContainer
                    ),
                ) {
                    Text(
                        text = label,
                        color = if (currentStatus == status)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

        val filteredTours = remember(currentStatus) {
            if (currentStatus.isNotBlank()) {
                FakeData.fakeMyTour.filter { it.status.toString() == currentStatus }
            } else {
                FakeData.fakeMyTour
            }
        }

        AnimatedVisibility(
            visible = filteredTours.isNotEmpty(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .animateContentSize()) {
                filteredTours.forEach { tour ->
                    MyTourItem(tour = tour, modifier = Modifier.clickable {
                        onNavigate(Route.DETAIL_SCREEN.toString())
                    })
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewMyTourScreen(
    modifier: Modifier = Modifier
) {

    TourBookingTheme {
        MyTourScreen() {}
    }

}