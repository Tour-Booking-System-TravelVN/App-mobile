package com.tanh.tourbooking.presentation.home

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tanh.tourbooking.R
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.ScreenOrientation
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.ui.theme.lightGray
import com.tanh.tourbooking.util.FakeData
import com.tanh.tourbooking.util.Route

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    onNavigate: (String) -> Unit
) {

    val state = viewModel.state.collectAsState().value
    val lastname = state.information?.lastname ?: "bạn"

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is OneTimeEvent.Navigate -> onNavigate(event.route)
                OneTimeEvent.PopBackStack -> Unit
                is OneTimeEvent.ShowSnackbar -> Unit
                is OneTimeEvent.ShowToast -> Unit
                is OneTimeEvent.OpenLink -> Unit
            }
        }
    }


    if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitHomeScreen(modifier, state, viewModel, lastname)
    } else {
        PortraitHomeScreen(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            state,
            viewModel,
            lastname
        )
    }
}

@Composable
fun PortraitHomeScreen(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    viewModel: HomeViewModel,
    lastname: String
) {

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
    var currentFilter by remember {
        mutableStateOf("")
    }


    val originalTour = remember { FakeData.fakePlacesVietNam }
    var tour by remember { mutableStateOf(originalTour) }

    LaunchedEffect(inputText) {
        tour = if (inputText.isNotBlank()) {
            originalTour.filter { it.name.contains(inputText, ignoreCase = true) }
        } else {
            originalTour
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
//            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(8.dp))
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Xin chào, $lastname",
                fontSize = titleTextSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = painterResource(R.drawable.dio),
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
                Bạn đang muốn
                đi đâu?
            """.trimIndent(),
            fontSize = headerTextSize,
            fontWeight = FontWeight.Bold
        )

        // Search Bar
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            placeholder = { Text("Khám phá thành phố", color = Color.LightGray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
        )

        // Explore Cities
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium2))
        Text(
            text = "Thành phố",
            fontWeight = FontWeight.Bold,
            fontSize = titleTextSize
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2)
        ) {
            items(listOf("Tất cả", "Phổ biến", "Đề xuất")) { item ->
                Text(
                    text = item,
                    fontSize = bodyTextSize,
                    fontWeight = if (item == currentFilter) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.clickable {
                        if (currentFilter == item) {
                            tour = FakeData.fakePlacesVietNam
                            currentFilter = ""
                        } else {
                            tour = FakeData.popularTour
                            currentFilter = item
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

        // Places List
        Box(modifier = Modifier.fillMaxWidth()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2)
            ) {
                items(tour) { place ->
                    PlaceItem(
                        fakePlace = place,
                        modifier = Modifier
                            .clickable {
                                viewModel.onEvent(
                                    HomeEvent.OnNavToTours(Route.TOUR_LIST_SCREEN.toString() + "/${place.name}")
                                )
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
                text = "Danh mục",
                fontWeight = FontWeight.Bold,
                fontSize = titleTextSize
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.clickable { isSeeAll = !isSeeAll },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Xem tất cả",
                    fontSize = bodyTextSize,
                    color = if (isSeeAll) MaterialTheme.colorScheme.onSurface else lightGray
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = if (isSeeAll) MaterialTheme.colorScheme.onSurface else lightGray,
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
                    CategoryItem(
                        fakeCategory = category,
                        navigate = {
                            viewModel.onNavToCategoryScreen(it)
                        }
                    )
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
                                CategoryItem(
                                    fakeCategory = category,
                                    navigate = {
                                        viewModel.onNavToCategoryScreen(it)
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                    }
                }
            }
        }
    }
}

