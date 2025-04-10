package com.tanh.tourbooking.presentation.tour_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tanh.tourbooking.data.model.dto.tour.TourProgram
import com.tanh.tourbooking.presentation.explore.TourProgramItem
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.FakeData

@Composable
fun TourProgramItem2(
    tour: TourProgram,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = tour.image.first(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(MaterialTheme.shapes.medium)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(MaterialTheme.dimens.small2)
                )
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
                Text(
                    text = "${tour.rated.toString()}/5.0",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
                Text(
                    text = "(${tour.totalRate})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
            Text(
                text = tour.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.W500
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
            Text(
                text = tour.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.LightGray
            )
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewTourProgramItem2(modifier: Modifier = Modifier) {
    TourBookingTheme {
        TourProgramItem2(
            tour = FakeData.fakeTourPrograms[0]
        )
    }
}