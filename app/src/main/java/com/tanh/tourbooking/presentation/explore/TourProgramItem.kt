package com.tanh.tourbooking.presentation.explore

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
import androidx.compose.material3.Surface
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
import coil.compose.AsyncImage
import com.tanh.tourbooking.data.model.dto.faketour.FakeTourProgram
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.FakeData

@Composable
fun TourProgramItem(
    fakeTourProgram: FakeTourProgram,
    modifier: Modifier = Modifier
) {

    Surface {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = fakeTourProgram.image.first(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .size(MaterialTheme.dimens.large)

            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = fakeTourProgram.name,
                        maxLines = 2,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow
                    )
                    Text(
                        text = fakeTourProgram.rated.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = fakeTourProgram.description,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewTourProgram() {
    TourBookingTheme {
        TourProgramItem(
            fakeTourProgram = FakeData.fakeFakeFakeTourPrograms[1]
        )
    }
}