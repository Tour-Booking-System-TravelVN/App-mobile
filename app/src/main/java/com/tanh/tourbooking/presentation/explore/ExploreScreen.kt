package com.tanh.tourbooking.presentation.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.R
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.FakeData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(modifier: Modifier = Modifier) {

    var inputDestination by remember {
        mutableStateOf("")
    }

    var isFiltered by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        //image
        HeaderSection(inputDestination)

        //modifiedSection
        ModifiedSection(isFiltered = isFiltered)

        //tourSection
        RecommendedTourSection()
    }

}

@Composable
fun RecommendedTourSection() {
    Text(
        text = "Suggestion",
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(
            start = MaterialTheme.dimens.small1,
            top = MaterialTheme.dimens.small2
        ),
    )
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small1)
    ) {
        items(FakeData.fakeTourPrograms) { tourProgram ->
            TourProgramItem(tourProgram)
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

    }
}

@Composable
fun ModifiedSection(
    isFiltered: Boolean,
    modifier: Modifier = Modifier
) {

    if (isFiltered) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
                    .padding(top = 16.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            ) {

            }
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HeaderSection(inputDestination: String) {
    var inputDestination1 = inputDestination
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
    ) {
        val width = maxWidth
        val height = maxHeight

        val gradient = Brush.horizontalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            )
        )
        Image(
            painter = painterResource(id = R.drawable.beach),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .width(width)
//                    .height(height * 0.7f)
                .aspectRatio(2f)
        )
        Column(
            modifier = Modifier
                .background(gradient)
                .width(width)
                .height(height)
                .aspectRatio(2f)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose Your Favorite",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            Text(
                text = "Many Interesting Choices For You",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        TextField(
            value = inputDestination1,
            onValueChange = {
                inputDestination1 = it
            },
            placeholder = {
                Text(
                    text = "Search your destination",
                    color = Color.LightGray
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                containerColor = Color.White
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            },
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExploreScreen(modifier: Modifier = Modifier) {
    TourBookingTheme {
        ExploreScreen()
    }
}