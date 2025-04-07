package com.tanh.tourbooking.presentation.home

import android.content.res.Configuration
import android.media.Image
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import coil.compose.AsyncImage
import com.tanh.tourbooking.ui.theme.ScreenOrientation
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.FakeData
import com.tanh.tourbooking.util.Route

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNav: (String) -> Unit
) {

    if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitHomeScreen(modifier, onNav = {
            onNav(it)
        })
    } else {
        PortraitHomeScreen(modifier = Modifier.verticalScroll(rememberScrollState()), onNav = {
            onNav(it)
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortraitHomeScreen(
    modifier: Modifier = Modifier,
    onNav: (String) -> Unit
) {
    val customerName = "Hey guys"
    var inputText by remember { mutableStateOf("") }
    var isSeeAll by remember { mutableStateOf(false) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (isSeeAll) 90f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val headerTextSize = (screenWidth * 0.06).sp
    val titleTextSize = (screenWidth * 0.05).sp
    val bodyTextSize = (screenWidth * 0.04).sp

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hi, $customerName",
                fontSize = titleTextSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            AsyncImage(
                model = "https://i.ibb.co/HpryKNLf/justin.jpg",
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
        Text(
            text = """
                Where do you want 
                to go?
            """.trimIndent(),
            fontSize = headerTextSize,
            fontWeight = FontWeight.Bold
        )

        // Search Bar
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            placeholder = { Text("Discover a city", color = Color.LightGray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
        )

        // Explore Cities
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium2))
        Text(
            text = "Explore Cities",
            fontWeight = FontWeight.Bold,
            fontSize = titleTextSize
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2)
        ) {
            items(listOf("All", "Popular", "Recommended")) { item ->
                Text(
                    text = item,
                    fontSize = bodyTextSize,
                    fontWeight = if (item == "Popular") FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

        // Places List
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val cardWidth = maxWidth * 0.4f

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2)
            ) {
                items(FakeData.fakePlaces) { place ->
                    PlaceItem(
                        place = place,
                        modifier = Modifier
                            .clickable {
                                onNav(Route.TOUR_LIST_SCREEN.toString())
                            }
                    )
                }
            }
        }

        // Categories
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium2))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Categories",
                fontWeight = FontWeight.Bold,
                fontSize = titleTextSize
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.clickable { isSeeAll = !isSeeAll },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "See all",
                    fontSize = bodyTextSize,
                    color = if (isSeeAll) MaterialTheme.colorScheme.onSurface else Color.LightGray
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = if (isSeeAll) MaterialTheme.colorScheme.onSurface else Color.LightGray,
                    modifier = Modifier.rotate(rotationAngle)
                )
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

        if (!isSeeAll) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2)
            ) {
                items(FakeData.fakeCategories) { category ->
                    CategoryItem(category = category)
                }
            }
        } else {
            AnimatedVisibility(visible = isSeeAll) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    FakeData.fakeCategories.chunked(3).forEach { rowCategories ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2)
                        ) {
                            rowCategories.forEach { category ->
                                CategoryItem(category = category)
                            }
                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen(modifier: Modifier = Modifier) {
    TourBookingTheme {
        HomeScreen(
            onNav = {}
        )
    }
}