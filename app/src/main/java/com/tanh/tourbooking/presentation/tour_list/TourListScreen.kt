package com.tanh.tourbooking.presentation.tour_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.FakeData
import com.tanh.tourbooking.util.Route

@Composable
fun TourListScreen(
    modifier: Modifier = Modifier,
    onNav: (String) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.small2)
    ) {
        Text(
            text = "Ninh Bình, Việt Nam",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        Text(
            text = "There are 100 interesting tours in Ninh Bình",
            style = MaterialTheme.typography.titleMedium,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))
        LazyColumn(
            contentPadding = PaddingValues(MaterialTheme.dimens.small2),
            modifier = Modifier.weight(1f)
        ) {
            items(FakeData.fakeTourPrograms) { tour ->
                TourProgramItem2(
                    tour = tour,
                    modifier = Modifier.padding(bottom = MaterialTheme.dimens.small1).clickable {
                        onNav(Route.DETAIL_SCREEN.toString())
                    }
                )
            }
        }
    }

}