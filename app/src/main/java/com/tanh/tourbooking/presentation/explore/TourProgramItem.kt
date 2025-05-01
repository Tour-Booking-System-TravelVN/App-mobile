package com.tanh.tourbooking.presentation.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tanh.tourbooking.data.model.dto.faketour.FakeTourProgram
import com.tanh.tourbooking.domain.model.TourUnit
import com.tanh.tourbooking.ui.theme.TextStyle17
import com.tanh.tourbooking.ui.theme.TextStyle18
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.Calculation
import com.tanh.tourbooking.util.FakeData

@Composable
fun TourProgramItem(
    tourUnit: TourUnit,
    modifier: Modifier = Modifier
) {

    Surface {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = tourUnit.tour.imageSet.first().url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .size(100.dp)

            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = tourUnit.tour.tourName,
                        maxLines = 2,
                        style = TextStyle17,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(MaterialTheme.dimens.small1))
                if (tourUnit.festival != null) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(4.dp)
                    ) {
                        Text(
                            text = tourUnit.festival.festivalName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Spacer(Modifier.height(MaterialTheme.dimens.small1))
                if (tourUnit.discount != null) {
                    Row(
                        verticalAlignment = Alignment.Top
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "đ",
                                textDecoration = TextDecoration.Underline,
                                style = TextStyle18,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = Calculation.formatDouble(
                                    Calculation.discountedPrice(
                                        amount = tourUnit.adultTourPrice,
                                        discount = tourUnit.discount
                                    )
                                ),
                                style = TextStyle18,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(Modifier.width(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "đ",
                                textDecoration = TextDecoration.combine(
                                    listOf(
                                        TextDecoration.Underline,
                                        TextDecoration.LineThrough
                                    )
                                ),
                                style = TextStyle17,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.alpha(0.4f)
                            )
                            Text(
                                text = Calculation.formatDouble(
                                    tourUnit.adultTourPrice
                                ),
                                style = TextStyle17,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.alpha(0.4f)
                            )
                        }
                    }
                }
            }
        }
    }

}

