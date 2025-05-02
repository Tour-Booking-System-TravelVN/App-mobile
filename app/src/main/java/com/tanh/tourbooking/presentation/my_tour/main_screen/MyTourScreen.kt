package com.tanh.tourbooking.presentation.my_tour.main_screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.tanh.tourbooking.domain.model.MyTour
import com.tanh.tourbooking.presentation.my_tour.MyTourUiState
import com.tanh.tourbooking.presentation.my_tour.MyTourViewModel
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.TextStyle17
import com.tanh.tourbooking.ui.theme.TextStyle18
import com.tanh.tourbooking.ui.theme.TextStyle20
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.FakeData
import com.tanh.tourbooking.util.Route
import com.tanh.tourbooking.util.TourStatus
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun MyTourScreen(
    modifier: Modifier = Modifier,
    viewModel: MyTourViewModel,
    onNavigate: (String) -> Unit,
    showSnackBar: (String) -> Unit
) {

    val state = viewModel.state.collectAsState(initial = MyTourUiState()).value

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState { 2 }
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val indicatorWidthDp = screenWidthDp / 2
    val offsetX by remember {
        derivedStateOf {
            (pagerState.currentPage + pagerState.currentPageOffsetFraction) * indicatorWidthDp
        }
    }

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is OneTimeEvent.Navigate -> onNavigate(event.route)
                OneTimeEvent.PopBackStack -> Unit
                is OneTimeEvent.ShowSnackbar -> showSnackBar(event.message)
                is OneTimeEvent.ShowToast -> Unit
                is OneTimeEvent.OpenLink -> Unit
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(Modifier.height(MaterialTheme.dimens.small2))
        //header
        Text(
            text = "Hoạt động",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small2)
        )
        Spacer(Modifier.height(MaterialTheme.dimens.small2))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.small1),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Đang diễn ra",
                style = TextStyle17,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(if (pagerState.currentPage == 0) 1f else 0.4f)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }
            )
            Text(
                text = "Lịch sử",
                style = TextStyle17,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(if (pagerState.currentPage == 1) 1f else 0.4f)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
            )
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Box(
                modifier = Modifier
                    .offset(x = offsetX)
                    .width(screenWidthDp / 2)
                    .height(4.dp)
                    .background(MaterialTheme.colorScheme.secondary)
            )
        }

        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ){
                when (page) {
                    0 -> OPWTour(list = state.opwTours) {
                        viewModel.onNavToDetailMyTour(it)
                    }
                    1 -> DoneTour(list = state.doneTours) {
                        viewModel.onNavToDetailMyTour(it)
                    }
                }
            }
        }
    }

}

@Composable
fun DoneTour(
    modifier: Modifier = Modifier,
    list: List<MyTour>,
    onClick: (MyTour) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(list) { myTour ->
            MyTourItem(
                myTour = myTour,
                modifier = Modifier.clickable {
                    onClick(myTour)
                }
            )
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
        }
    }
}

@Composable
fun OPWTour(
    modifier: Modifier = Modifier,
    list: List<MyTour>,
    onClick: (MyTour) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(list) { myTour ->
            MyTourItem(
                myTour = myTour,
                modifier = Modifier.clickable {
                    onClick(myTour)
                }
            )
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
        }
    }
}




@Preview(showSystemUi = true)
@Composable
fun PreviewMyTourScreen(
    modifier: Modifier = Modifier
) {

}