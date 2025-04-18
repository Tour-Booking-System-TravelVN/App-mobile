package com.tanh.tourbooking.presentation.detail_tour.item

import android.widget.RatingBar
import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.domain.model.Rating

@Composable
fun RatingItem(
    modifier: Modifier = Modifier,
    rating: Rating
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingBarDisplay(
                    size = 15.dp,
                    averageRating = rating.ratingValue.toDouble()
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = rating.fullName,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = rating.comment,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}