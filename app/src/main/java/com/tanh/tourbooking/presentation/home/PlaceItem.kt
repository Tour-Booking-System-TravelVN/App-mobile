package com.tanh.tourbooking.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tanh.tourbooking.data.model.dto.tour.Place
import com.tanh.tourbooking.ui.theme.TourBookingTheme

@Composable
fun PlaceItem(
    place: Place,
    modifier: Modifier = Modifier
) {

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box {

            AsyncImage(
                model = place.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(220.dp)
                    .width(180.dp)
                    .align(Alignment.Center)
                    .border(
                        4.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
            )

            Box(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(12.dp))
                    .width(160.dp)
                    .background(Color(0xFFE9EFF1).copy(alpha = 0.5f)),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    modifier = Modifier.wrapContentHeight().padding(start = 8.dp)
                ) {
                    Text(
                        text = place.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black
                    )
                    Text(
                        text = place.country,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black
                    )
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewPlaceItem(modifier: Modifier = Modifier) {
    TourBookingTheme {
        PlaceItem(
            place = Place(
                name = "Hanoi",
                country = "Vietnam",
                imageUrl = ""
            )
        )
    }
}