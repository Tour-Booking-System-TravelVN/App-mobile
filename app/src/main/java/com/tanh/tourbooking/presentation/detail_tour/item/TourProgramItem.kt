package com.tanh.tourbooking.presentation.detail_tour.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.R
import com.tanh.tourbooking.domain.model.TourProgram
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.ui.theme.greenItem
import com.tanh.tourbooking.util.toStringList

@Composable
fun TourProgramItem(
    modifier: Modifier = Modifier,
    tourProgram: TourProgram
) {
    val list = tourProgram.description.toStringList()
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TourProgramItemItem(
            icon = painterResource(R.drawable.destination),
            title = tourProgram.locations
        )
        Spacer(Modifier.height(MaterialTheme.dimens.small2))
        TourProgramItemItem(
            icon = painterResource(R.drawable.meal),
            title = tourProgram.mealDescription
        )
        Spacer(Modifier.height(MaterialTheme.dimens.small2))
        Row {
            Icon(
                painter = painterResource(R.drawable.information),
                contentDescription = null,
                tint = greenItem,
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.width(10.dp))
            Column {
                list.forEach { content ->
                    Text(
                        text = "• $content",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }

}

@Composable
fun TourProgramItemItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String
) {
    Row {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = greenItem,
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}