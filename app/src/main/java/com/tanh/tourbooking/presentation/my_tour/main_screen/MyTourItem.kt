package com.tanh.tourbooking.presentation.my_tour.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tanh.tourbooking.data.mappers.toIndicatorString
import com.tanh.tourbooking.domain.model.MyTour
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.ui.theme.lighterGray
import com.tanh.tourbooking.util.BookingStatus
import com.tanh.tourbooking.util.Calculation

@Composable
fun MyTourItem(
    modifier: Modifier = Modifier,
    myTour: MyTour
) {

    Column(
        modifier = modifier
            .padding(horizontal = MaterialTheme.dimens.small2)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = lighterGray,
                shape = MaterialTheme.shapes.medium
            )
            .padding(MaterialTheme.dimens.small1)
    ) {
        //row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = myTour.tourUnit.tour.imageSet.first().url,
                contentDescription = null,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .size(50.dp)
            )
            Spacer(Modifier.width(5.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = myTour.bookingDate.toIndicatorString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.alpha(0.5f)
                )
                Text(
                    text = "${Calculation.formatDouble(myTour.totalAmount)} đ",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(Modifier.weight(1f))
            StatusIndicator(status = myTour.status)
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        Text(
            text = myTour.tourUnit.tour.tourName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = myTour.tourUnit.tour.departurePlace,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.PeopleAlt,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "${myTour.adultNumber + myTour.childNumber + myTour.toddlerNumber + myTour.babyNumber} người",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}

@Composable
fun StatusIndicator(
    modifier: Modifier = Modifier,
    status: String
) {
    val bookingStatus = BookingStatus.fromCode(status)

    if (bookingStatus != null) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(bookingStatus.color, CircleShape)
                .padding(4.dp)
        ) {
            Text(
                text = bookingStatus.description,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}