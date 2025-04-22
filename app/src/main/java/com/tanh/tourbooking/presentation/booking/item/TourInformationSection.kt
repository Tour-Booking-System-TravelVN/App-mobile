package com.tanh.tourbooking.presentation.booking.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tanh.tourbooking.data.mappers.toFormattedString
import com.tanh.tourbooking.presentation.booking.BookingUiState
import com.tanh.tourbooking.ui.theme.TextStyle17
import com.tanh.tourbooking.ui.theme.dimens

@Composable
fun TourInformationSection(
    modifier: Modifier = Modifier,
    state: BookingUiState
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small2)
    ) {
        Text(
            text = state.state.tourName,
            style = TextStyle17
        )
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        Text(
            text = state.state.departureDate?.toFormattedString() ?: "@@",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        Text(
            text = "Người lớn x ${state.state.adultNumber}",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        if (state.state.childNumber > 0) {
            Text(
                text = "Trẻ em (4 - 13) x ${state.state.childNumber}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        if (state.state.toddleNumber > 0) {
            Text(
                text = "Trẻ em (1 - 3) x ${state.state.toddleNumber}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        if (state.state.babyNumber > 0) {
            Text(
                text = "Em bé x ${state.state.babyNumber}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        Text(
            text = "Hủy miễn phí 24 giờ",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }

}
