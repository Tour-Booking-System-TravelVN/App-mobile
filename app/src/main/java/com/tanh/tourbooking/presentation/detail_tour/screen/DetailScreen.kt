package com.tanh.tourbooking.presentation.detail_tour.screen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tanh.tourbooking.domain.model.Discount
import com.tanh.tourbooking.domain.model.TourUnit
import com.tanh.tourbooking.presentation.detail_tour.DetailEvent
import com.tanh.tourbooking.presentation.detail_tour.DetailUiState
import com.tanh.tourbooking.presentation.detail_tour.DetailViewModel
import com.tanh.tourbooking.presentation.detail_tour.item.BookingBarSection
import com.tanh.tourbooking.presentation.detail_tour.item.BottomSheet
import com.tanh.tourbooking.presentation.detail_tour.item.CheckSection
import com.tanh.tourbooking.presentation.detail_tour.item.DiscountItem
import com.tanh.tourbooking.presentation.detail_tour.item.IndicatorItem
import com.tanh.tourbooking.presentation.detail_tour.item.RatingBarDisplay
import com.tanh.tourbooking.presentation.detail_tour.item.RatingItem
import com.tanh.tourbooking.presentation.detail_tour.item.TourProgramItem
import com.tanh.tourbooking.presentation.detail_tour.item.UnCheckSection
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.ui.theme.lightGray
import com.tanh.tourbooking.ui.theme.lighterGray
import com.tanh.tourbooking.ui.theme.starColor
import com.tanh.tourbooking.util.Calculation
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel<DetailViewModel>(),
    showSnackBar: (String) -> Unit,
    popBackStack: () -> Unit,
    onNavigate: (String) -> Unit
) {

    val state = viewModel.state.collectAsState(initial = DetailUiState()).value
    val calendarState = viewModel.calendar.collectAsState().value

    val tourUnit = state.tourUnit

    LaunchedEffect(Unit) {
        viewModel.booking.collect { state ->
            Log.d("BO4", "abc: $state")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is OneTimeEvent.Navigate -> onNavigate(event.route)
                OneTimeEvent.PopBackStack -> popBackStack()
                is OneTimeEvent.ShowSnackbar -> showSnackBar(event.message)
                is OneTimeEvent.ShowToast -> Unit
                is OneTimeEvent.OpenLink -> Unit
            }
        }
    }

    var isExpandedSchedule by remember {
        mutableStateOf(false)
    }

    val direction by animateFloatAsState(
        targetValue = if (isExpandedSchedule) 90f else 0f,
        animationSpec = tween(300)
    )


    val bottomSheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember {
        mutableStateOf(false)
    }

    val imageSet = tourUnit?.tour?.imageSet ?: emptyList()

    val pagerState = rememberPagerState(pageCount = {
        imageSet.size
    })

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp - 60.dp
    val visibleTopPadding = 48.dp
    val minHeight = screenHeight * 0.6f
    val maxHeight = screenHeight - visibleTopPadding

    var currentHeight by remember { mutableStateOf(minHeight) }
    val animatedHeight by animateDpAsState(
        targetValue = currentHeight
    )
    val offsetX by remember {
        derivedStateOf {
            (pagerState.currentPage + pagerState.currentPageOffsetFraction) * 12.dp
        }
    }
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BookingBarSection(
                amount = state.tourUnit?.adultTourCost ?: 0.0
            ) {
                isSheetOpen = true
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            //image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight - animatedHeight)
                    .then(
                        if (currentHeight == maxHeight) Modifier.pointerInput(Unit) {
                            detectVerticalDragGestures(
                                onDragEnd = {
                                    currentHeight =
                                        if (currentHeight < (minHeight + maxHeight) / 2) {
                                            minHeight
                                        } else {
                                            maxHeight
                                        }
                                },
                                onVerticalDrag = { _, dragAmount ->
                                    currentHeight = (currentHeight - dragAmount.dp)
                                        .coerceIn(minHeight, maxHeight)
                                }
                            )
                        } else Modifier
                    )
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.Center)
                ) { page ->
                    AsyncImage(
                        model = imageSet[page].url,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.1f)
                    )
                }
                IconButton(
                    onClick = {
                        viewModel.popBackStack()
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier
//                    .border(1.dp, Color(0xFFefecf0), CircleShape)
                        .clip(CircleShape)
                        .padding(8.dp)
                        .alpha(0.5f)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) lighterGray else Color.DarkGray
                        val isSwap by derivedStateOf {
                            pagerState.currentPage == iteration
                        }
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .animateContentSize()
                                .background(color)
                                .offset(x = offsetX)
                                .size(
                                    height = MaterialTheme.dimens.small1,
                                    width = if (isSwap) MaterialTheme.dimens.small3 else MaterialTheme.dimens.small1
                                )
                        )
                    }
                }
            }

            //detail tour section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(animatedHeight)
                    .align(Alignment.BottomCenter)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onDragEnd = {
                                currentHeight = if (currentHeight < (minHeight + maxHeight) / 2) {
                                    minHeight
                                } else {
                                    maxHeight
                                }
                            },
                            onVerticalDrag = { _, dragAmount ->
                                currentHeight = (currentHeight - dragAmount.dp)
                                    .coerceIn(minHeight, maxHeight)
                            }
                        )
                    }
            ) {
                Column(
                    modifier = Modifier
                        .padding(0.dp)
                        .wrapContentHeight()
                        .let {
                            if (currentHeight == maxHeight) {
                                it.verticalScroll(scrollState)
                            } else it
                        }
                ) {
                    //rate + price
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
                    //name
                    Text(
                        text = tourUnit?.tour?.tourName ?: "No name",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small1)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = MaterialTheme.dimens.small2,
                                end = MaterialTheme.dimens.small2
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = starColor,
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = Calculation.averageRatings(state.ratings).toString(),
                            fontSize = 18.sp,
                            color = starColor,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "(",
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Light
                        )
                        Text(
                            text = "${state.ratings.size} Đánh giá",
                            fontSize = 18.sp,
                            textDecoration = TextDecoration.Underline,
                            color = Color.Black,
                            fontWeight = FontWeight.Light
                        )
                        Text(
                            text = ")",
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Light
                        )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
                    HorizontalDivider(
                        thickness = 0.5.dp,
                        color = Color.LightGray,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

                    //detail
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.dimens.small1)
                    ) {
                        IndicatorSection(state.tourUnit)
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
                    //Discount
                    DiscountSection(state.tourUnit?.discount)
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
                    //Description
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.dimens.small1)
                    ) {
                        Text(
                            text = "Mô tả",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                        Text(
                            text = tourUnit?.tour?.description ?: "This tour is amazing",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.LightGray
                        )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                    //schedule
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.dimens.small1)
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Lịch trình",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(
                                onClick = {
                                    isExpandedSchedule = !isExpandedSchedule
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.rotate(direction)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                        if (isExpandedSchedule) {
                            Column(
                                Modifier
                                    .animateContentSize()
                                    .background(MaterialTheme.colorScheme.surface)
                            ) {
                                state.tourProgram.forEach { tourProgram ->
                                    TourProgramItem(tourProgram = tourProgram)
                                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                                    HorizontalDivider(
                                        Modifier.fillMaxWidth(),
                                        0.5.dp,
                                        Color.LightGray
                                    )
                                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                                }
                            }
                        }
                    }
//            HorizontalDivider(
//                thickness = 0.5.dp,
//                color = Color.LightGray,
//                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small2)
//            )
                    //tourguide
//            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
//            Text(
//                text = "FakeTour guide",
//                style = MaterialTheme.typography.headlineMedium,
//                color = Color.Black
//            )
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = tourProgram.fakeTourGuide.name,
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = Color.Black
//                )
//                Spacer(modifier = Modifier.weight(1f))
//                Text(
//                    text = tourProgram.fakeTourGuide.phone.toString(),
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = Color.Black
//                )
//            }

                    //inclusive
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.dimens.small1)
                    ) {
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
                        Text(
                            text = "Tour này bao gồm",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                        CheckSection(check = state.tourUnit?.tour?.inclusions ?: "")
                        //exclusive
                        UnCheckSection(uncheck = state.tourUnit?.tour?.exclusions ?: "")

                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
                    }
                    HorizontalDivider(
                        thickness = 0.5.dp,
                        color = lightGray,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
                    //rating
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.dimens.small1)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                Modifier
                                    .width(6.dp)
                                    .height(25.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .background(MaterialTheme.colorScheme.secondary)
                            )
                            Spacer(Modifier.width(MaterialTheme.dimens.small2))
                            Text(
                                text = "Đánh giá",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(Modifier.width(MaterialTheme.dimens.small3))
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val averageRating = Calculation.averageRatings(state.ratings)
                                Row(
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Text(
                                        text = averageRating.toString(),
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = "/5",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Color(0xFFa7a6a7)
                                    )
                                }
                                Spacer(Modifier.width(MaterialTheme.dimens.small3))
                                RatingBarDisplay(
                                    size = 25.dp,
                                    averageRating = averageRating
                                )
                            }
                            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                            Text(
                                text = "Dựa trên ${state.ratings.size} lượt đánh giá",
                                style = MaterialTheme.typography.bodyMedium,
                                color = lightGray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.dimens.medium1)
                    ) {
                        state.ratings.forEach { rating ->
                            RatingItem(rating = rating)
                            Spacer(Modifier.height(MaterialTheme.dimens.small2))
                        }
                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))

                    //bottom sheet
                    if (isSheetOpen) {
                        BottomSheet(
                            tourUnit = state.tourUnit,
                            bottomSheetState = bottomSheetState,
                            onSheetStateChange = {
                                isSheetOpen = it
                            },
                            calendarState = calendarState,
                            discount = state.tourUnit?.discount,
                            dismissSheet = {
                                isSheetOpen = false
                            },
                            onCalendar = {
                                viewModel.onEvent(DetailEvent.OnLoadCalendar)
                            },
                            bookTour = {
                                viewModel.onEvent(DetailEvent.BookTour(it))
                            }
                        )
                    }
                }
            }
        }

    }

}

@Composable
fun DiscountSection(discount: Discount?) {
    if (discount != null) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(lighterGray)
            ) { }
            Row(
                Modifier
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ưu đãi cho bạn",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.width(8.dp))
                DiscountItem(discountName = discount.discountName)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(lighterGray)
            ) { }
        }
    }
}


@Composable
fun PeopleSelectorItem(
    modifier: Modifier = Modifier,
    isAdult: Boolean = false,
    title: String,
    count: Int,
    price: Double,
    onValueChange: (Int) -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxWidth().padding(MaterialTheme.dimens.small2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title
        )
        Spacer(Modifier.weight(1f))
        if(price != 0.0) {
            Text(
                text = "đ",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = Calculation.formatDouble(price),
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(Modifier.width(6.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(30.dp)
                .border(1.dp, Color.LightGray, CircleShape)
                .clip(CircleShape)
                .clickable {
                    if(isAdult) {
                        if (count > 1) {
                            onValueChange(count - 1)
                        }
                    } else {
                        if (count > 0) {
                            onValueChange(count - 1)
                        }
                    }
                }

        ) {
            Text(
                text = "-"
            )
        }
        Spacer(Modifier.width(MaterialTheme.dimens.small1))
        Text(
            text = count.toString()
        )
        Spacer(Modifier.width(MaterialTheme.dimens.small1))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(30.dp)
                .border(1.dp, Color.LightGray, CircleShape)
                .clip(CircleShape)
                .clickable {
                    onValueChange(count + 1)
                }
        ) {
            Text(
                text = "+"
            )
        }
    }
}

@Composable
private fun IndicatorSection(
    tour: TourUnit?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            IndicatorItem(
                icon = Icons.Default.AirplanemodeActive,
                title = tour?.tour?.vehicle ?: "Xe khách"
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            IndicatorItem(
                icon = Icons.Default.Place,
                title = tour?.tour?.departurePlace ?: "Việt Nam"
            )
        }
        Spacer(Modifier.width(5.dp))
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            IndicatorItem(
                icon = Icons.Default.AccessTime,
                title = Calculation.formatDuration(tour?.tour?.duration ?: "")
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            IndicatorItem(
                icon = Icons.Default.People,
                title = tour?.availableCapacity.toString()
            )
        }
    }
}

