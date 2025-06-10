package com.tanh.tourbooking.presentation.my_tour.detail_screen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CancelPresentation
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.tanh.tourbooking.R
import com.tanh.tourbooking.data.mappers.toIndicator
import com.tanh.tourbooking.data.mappers.toIndicatorString
import com.tanh.tourbooking.presentation.my_tour.MyTourUiState
import com.tanh.tourbooking.presentation.my_tour.MyTourViewModel
import com.tanh.tourbooking.presentation.my_tour.main_screen.StatusIndicator
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.TextStyle18
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.ui.theme.lightGray
import com.tanh.tourbooking.ui.theme.lighterGray
import com.tanh.tourbooking.ui.theme.starColor
import com.tanh.tourbooking.util.Calculation

@SuppressLint("UnrememberedMutableState")
@Composable
fun DetailMyTourScreen(
    modifier: Modifier = Modifier,
    viewModel: MyTourViewModel,
    onNavigate: (String) -> Unit,
    popBackStack: () -> Unit,
    showSnackBar: (String) -> Unit
) {

    val state = viewModel.state.collectAsState(initial = MyTourUiState()).value
    val myTour = state.currentTour
    val clipboardManager = LocalClipboardManager.current
    var showDialog by remember {
        mutableStateOf(false)
    }
    var rating by remember {
        mutableStateOf(5f)
    }
    var comment by remember {
        mutableStateOf("")
    }
    val emoji by derivedStateOf {
        when (rating) {
            1f -> "😭"
            2f -> "☹️"
            3f -> "😐"
            4f -> "😊"
            5f -> "😍"
            else -> "❓"
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

    if (myTour == null) {
        CircularProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
        ) {
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.small2),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            viewModel.popBackStack()
                        }
                )
                Text(
                    text = "Chi tiết",
                    style = TextStyle18,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.size(25.dp))
            }
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
            HorizontalDivider(
                Modifier.fillMaxWidth(),
                1.dp,
                lighterGray
            )
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.small2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = myTour.bookingDate.toIndicatorString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.weight(1f))
                StatusIndicator(status = myTour.status)
            }
            Spacer(Modifier.height(MaterialTheme.dimens.small1))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.small2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mã đặt tour",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = myTour.bookingId,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.width(2.dp))
                IconButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(myTour.bookingId))
                    },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.small2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mã chuyến đi",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = myTour.tourUnit.tourUnitId,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.width(2.dp))
                IconButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(myTour.tourUnit.tourUnitId))
                    },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
            //information section
            Box(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.dimens.small2)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = lightGray,
                        MaterialTheme.shapes.medium
                    )
                    .padding(
                        vertical = MaterialTheme.dimens.small2,
                        horizontal = MaterialTheme.dimens.small2
                    )
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.width(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.circle_dot_filled_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier.size(22.dp)
                            )
                            VerticalDivider(
                                modifier = Modifier
                                    .padding(vertical = 3.dp)
                                    .height(10.dp)
                                    .width(1.dp),
                                color = Color.LightGray
                            )
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = null,
                                tint = starColor,
                                modifier = Modifier.size(25.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = myTour.tourUnit.departureDate?.toIndicator() ?: "",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = myTour.tourUnit.returnDate?.toIndicator() ?: "",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccessTimeFilled,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = Calculation.formatDuration(myTour.tourUnit.tour.duration),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AirportShuttle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = myTour.tourUnit.tour.departurePlace,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AddAPhoto,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = myTour.tourUnit.tour.placesToVisit,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    Spacer(Modifier.height(MaterialTheme.dimens.small2))
                    HorizontalDivider(
                        Modifier.fillMaxWidth(),
                        1.dp,
                        lightGray
                    )
                    Spacer(Modifier.height(MaterialTheme.dimens.small2))
                    Column(
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(6.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.card),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.outlineVariant,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .align(Alignment.Center)
                                )
                            }
                            Spacer(Modifier.width(5.dp))
                            Text(
                                text = "Chuyển khoản",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(Modifier.weight(1f))
                            Text(
                                text = "${Calculation.formatDouble(myTour.totalAmount)}đ",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Spacer(Modifier.height(MaterialTheme.dimens.small1))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            Text(
                                text = "Người lớn x ${myTour.adultNumber}",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            if (myTour.childNumber > 0) {
                                Text(
                                    text = "Trẻ em x ${myTour.childNumber}",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                            if (myTour.babyNumber > 0) {
                                Text(
                                    text = "Em bé x ${myTour.babyNumber}",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                            if (myTour.toddlerNumber > 0) {
                                Text(
                                    text = "Trẻ nhỏ x ${myTour.toddlerNumber}",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
            //more detail
            Box(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.dimens.small2)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = lightGray,
                        MaterialTheme.shapes.medium
                    )
                    .padding(
                        vertical = MaterialTheme.dimens.small2,
                        horizontal = MaterialTheme.dimens.small2
                    )
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                                .padding(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier
                                    .size(25.dp)
                                    .align(Alignment.Center)
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Nhóm chat",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.W400,
                        )
                        Spacer(Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                viewModel.navToChatBox()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    if (myTour.status == "D") {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .padding(5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.StarRate,
                                    contentDescription = null,
                                    tint = starColor,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .align(Alignment.Center)
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Đánh giá",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.W400,
                            )
                            Spacer(Modifier.weight(1f))
                            IconButton(
                                onClick = {
                                    showDialog = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .padding(5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CancelPresentation,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .align(Alignment.Center)
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Hủy tour",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.W400,
                            )
                            Spacer(Modifier.weight(1f))
                            IconButton(
                                onClick = {
                                    viewModel.cancelTour(state.currentTour.bookingId)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                }

                if (showDialog) {
                    Dialog(
                        onDismissRequest = {
                            showDialog = false
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(0.dp)
                                .wrapContentSize()
                                .clip(MaterialTheme.shapes.large)
                                .background(Color.White)
                                .padding(MaterialTheme.dimens.small2),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = emoji,
                                fontSize = 40.sp
                            )
                            Spacer(Modifier.height(MaterialTheme.dimens.small2))
                            RatingBar(
                                value = rating,
                                style = RatingBarStyle.Fill(),
                                onValueChange = {
                                    rating = it
                                },
                                onRatingChanged = {

                                }
                            )
                            Spacer(Modifier.height(MaterialTheme.dimens.small1))
                            OutlinedTextField(
                                value = comment,
                                onValueChange = {
                                    comment = it
                                }
                            )
                            Spacer(Modifier.height(MaterialTheme.dimens.small2))
                            Row {
                                OutlinedButton(
                                    onClick = {
                                        showDialog = false
                                    },
                                    shape = MaterialTheme.shapes.medium
                                ) {
                                    Text("Hủy")
                                }
                                Spacer(Modifier.width(6.dp))
                                Button(
                                    onClick = {
                                        viewModel.ratingTour(
                                            comment = comment,
                                            ratingValue = rating.toInt()
                                        )
                                        comment = ""
                                        rating = 5f
                                        showDialog = false
                                    },
                                    shape = MaterialTheme.shapes.medium
                                ) {
                                    Text("Gửi đánh giá")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}