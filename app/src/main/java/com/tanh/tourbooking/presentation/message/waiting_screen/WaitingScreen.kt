package com.tanh.tourbooking.presentation.message.waiting_screen

import android.util.Log
import androidx.collection.intIntMapOf
import androidx.collection.scatterSetOf
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tanh.tourbooking.R
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.ui.theme.lighterGray

@Composable
fun WaitingScreen(
    modifier: Modifier = Modifier,
    viewModel: WaitingViewModel = hiltViewModel<WaitingViewModel>(),
    popBackStack: () -> Unit
) {

    val state = viewModel.state.collectAsState(initial = WaitingUiState()).value

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val density = LocalDensity.current

    var showModifier by remember {
        mutableStateOf(false)
    }

    var currentOffset by remember {
        mutableStateOf(Offset(0F, 0F))
    }

    var currentId by remember {
        mutableIntStateOf(-1)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is OneTimeEvent.Navigate -> Unit
                OneTimeEvent.PopBackStack -> popBackStack()
                is OneTimeEvent.ShowSnackbar -> Unit
                is OneTimeEvent.ShowToast -> Unit
                is OneTimeEvent.OpenLink -> Unit
            }
        }
    }


    Box {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            showModifier = false
                        }
                    )
                }
        ) {
            item {
                Spacer(Modifier.height(MaterialTheme.dimens.small2))
            }
            items(state.waitingIds) { id ->
                WaitingIdScreen(
                    id = id,
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = { offset ->
                                currentOffset = offset
                                showModifier = true
                                currentId = id
                            }
                        )
                    }
                )
            }
            item {

                AnimatedVisibility(
                    visible = showModifier,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    if (showModifier) {
                        CustomerModifier(
                            modifier = Modifier
                                .offset {
                                    IntOffset(
                                        x = with(density) { (screenWidth / 4).toPx().toInt() },
                                        y = currentOffset.y.toInt()
                                    )
                                },
                            onAccept = {
                                showModifier = false
                                viewModel.acceptUserId(currentId)
                            },
                            onCancel = {
                                showModifier = false
                                viewModel.refuseUserId(currentId)
                            }
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun WaitingIdScreen(
    modifier: Modifier = Modifier,
    id: Int,
) {
    Column(
    ) {
        Box(
            modifier = modifier
                .padding(0.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(
                    horizontal = MaterialTheme.dimens.small2,
                    vertical = MaterialTheme.dimens.small2
                )
        ) {
            Text(
                id.toString(),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        HorizontalDivider(
            Modifier.fillMaxWidth(),
            1.dp,
            lighterGray
        )
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
    }
}

@Composable
fun CustomerModifier(
    modifier: Modifier = Modifier,
    onAccept: () -> Unit,
    onCancel: () -> Unit
) {
    Row(
        modifier = modifier
            .width(LocalConfiguration.current.screenWidthDp.dp / 2)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(7.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(
            icon = painterResource(R.drawable.check),
            text = "Chấp nhận",
            onClick = onAccept,
            iconSize = 30.dp
        )

        ActionButton(
            icon = Icons.Default.Delete,
            text = "Chặn",
            onClick = onCancel,
            iconSize = 25.dp
        )
    }
}

@Composable
fun ActionButton(
    icon: Any, // Sử dụng Any để có thể truyền vào cả ImageVector và Painter
    text: String,
    onClick: () -> Unit,
    iconSize: Dp = 30.dp
) {
    Box(
        modifier = Modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (icon) {
                is ImageVector -> {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(iconSize)
                    )
                }
                is Painter -> {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
            Text(text)
        }
    }
}