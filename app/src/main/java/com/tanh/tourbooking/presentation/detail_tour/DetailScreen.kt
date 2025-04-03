package com.tanh.tourbooking.presentation.detail_tour

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tanh.tourbooking.data.model.dto.tour.TourProgram
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.FakeData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier
) {

    val tourProgram = FakeData.fakeTourPrograms[0]

    val bottomSheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember {
        mutableStateOf(false)
    }

    val pagerState = rememberPagerState(pageCount = {
        listImages.size
    })

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.wrapContentSize()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.Center)
            ) { page ->
                AsyncImage(
                    model = listImages[page],
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.1f)
                )
            }
            IconButton(
                onClick = { },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
//                    .border(1.dp, Color(0xFFefecf0), CircleShape)
                    .clip(CircleShape)
                    .padding(8.dp)
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
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(MaterialTheme.dimens.small1)
                    )
                }
            }
        }

        //detail tour section
        Column(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(MaterialTheme.dimens.small2)
                .verticalScroll(rememberScrollState())
        ) {
            //rate + price
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "★",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "4.5/5.0",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "(${tourProgram.totalRate})",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Price: $${tourProgram.price} VNĐ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))

            //name
            Text(
                text = tourProgram.name,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            HorizontalDivider(
                thickness = 0.5.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small2)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

            //detail
            IndicatorSection(tourProgram)

            //Description
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            Text(
                text = "Description",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            Text(
                text = tourProgram.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Schedule",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            Text(
                text = "ngày 1: 10h sáng đi hội an",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "ngày 2: 10h sáng đi hội an",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "ngày 3: 10h sáng đi hội an",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            HorizontalDivider(
                thickness = 0.5.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small2)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            Text(
                text = "Tour guide",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tourProgram.tourGuide.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = tourProgram.tourGuide.phone.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        isSheetOpen = true
                    }
                ) {
                    Text(
                        text = "Book now",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            //bottom sheet
            if (isSheetOpen) {
                BottomSheet(
                    bottomSheetState = bottomSheetState,
                    onSheetStateChange = {
                        isSheetOpen = it
                    }
                )
            }
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BottomSheet(
    bottomSheetState: SheetState,
    onSheetStateChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    var adultCount by remember {
        mutableStateOf(0)
    }
    var childCount by remember {
        mutableStateOf(0)
    }
    var babyCount by remember {
        mutableStateOf(0)
    }

    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = {
            onSheetStateChange(false)
        }
    ) {
        Column(
            Modifier
                .wrapContentSize()
                .padding(MaterialTheme.dimens.small2)
        ) {
            PeopleSelectorItem(
                title = "Adult",
                count = adultCount,
                onValueChange = {
                    adultCount = it
                }
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            HorizontalDivider(
                thickness = 0.5.dp,
                color = Color.LightGray,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            PeopleSelectorItem(
                title = "Child",
                count = childCount,
                onValueChange = {
                    childCount = it
                }
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            HorizontalDivider(
                thickness = 0.5.dp,
                color = Color.LightGray,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            PeopleSelectorItem(
                title = "Kids (Baby)",
                count = babyCount,
                onValueChange = {
                    babyCount = it
                }
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            HorizontalDivider(
                thickness = 0.5.dp,
                color = Color.LightGray,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {},
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                contentPadding = PaddingValues(MaterialTheme.dimens.small2)
            ) {
                Text(
                    text = "Reset"
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small3))
            Button(
                onClick = {},
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                contentPadding = PaddingValues(MaterialTheme.dimens.small2)
            ) {
                Text(
                    text = "Confirmation"
                )
            }
        }
    }
}

@Composable
fun PeopleSelectorItem(
    title: String,
    count: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    var count1 by remember {
        mutableIntStateOf(count)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title
        )
        Spacer(Modifier.weight(1f))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(30.dp)
                .border(1.dp, Color.LightGray, CircleShape)
                .clip(CircleShape)
                .clickable {
                    if (count > 0) {
                        onValueChange(count - 1)
                    }
                }
                .clickable {
                    if (count > 0) {
                        onValueChange(count - 1)
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
                    if (count > 0) {
                        onValueChange(count - 1)
                    }
                }
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
private fun IndicatorSection(tourProgram: TourProgram) {
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
                title = tourProgram.vehicle
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            IndicatorItem(
                icon = Icons.Default.Place,
                title = tourProgram.startDestination
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            IndicatorItem(
                icon = Icons.Default.AccessTime,
                title = tourProgram.duration
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            IndicatorItem(
                icon = Icons.Default.People,
                title = tourProgram.maxParticipant.toString()
            )
        }
    }
}


val listImages = listOf(
    "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg",
    "https://i.ibb.co/VcH7dy9d/ninhbinh2.jpg",
    "https://i.ibb.co/dwqzBVZL/jonnn.jpg",
    "https://i.ibb.co/ch8p9Pd1/image.png",
    "https://i.ibb.co/HpryKNLf/justin.jpg"
)