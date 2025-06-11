package com.tanh.tourbooking.presentation.explore

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tanh.tourbooking.R
import com.tanh.tourbooking.domain.model.TourUnit
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.TextStyle17
import com.tanh.tourbooking.ui.theme.TextStyle18
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.Calculation
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel,
    onNavigate: (String) -> Unit,
    showSnackbar: (String) -> Unit
) {

    val state = viewModel.state.collectAsState().value
//    var hasChanged by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is OneTimeEvent.Navigate -> onNavigate(event.route)
                OneTimeEvent.PopBackStack -> Unit
                is OneTimeEvent.ShowSnackbar -> showSnackbar(event.message)
                is OneTimeEvent.ShowToast -> Unit
                is OneTimeEvent.OpenLink -> Unit
            }
        }
    }

    var inputDestination by remember {
        mutableStateOf("")
    }

    var inputDate by remember {
        mutableStateOf("")
    }

    var isFiltered by remember {
        mutableStateOf(false)
    }

    var minPrice by remember {
        mutableDoubleStateOf(0.0)
    }

    var maxPrice by remember {
        mutableDoubleStateOf(5000000.0)
    }


    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        //image
        HeaderSection()

        //modifiedSection
        ModifiedSection(
            inputDestination = inputDestination,
            onValueChange = {
                inputDestination = it
                viewModel.onEvent(ExploreEvent.TypePlace(place = inputDestination))
            },
            isFiltered = isFiltered,
            onFilterChange = {
                isFiltered = it
            },
            inputDate = inputDate,
            onDateChange = {
                inputDate = it
                viewModel.onEvent(ExploreEvent.TypeDepartureDate(date = it))
            },
            minPrice = minPrice,
            maxPrice = maxPrice,
            onPriceChange = {
                minPrice = it.first
                maxPrice = it.second
                viewModel.onEvent(ExploreEvent.OnPriceRangeChange(minPrice, maxPrice))
            }
        )

        //tourSection
        RecommendedTourSection(state.tourUnitList) {
            viewModel.onNavToDetail(it)
        }
    }

}

@Composable
fun RecommendedTourSection(
    tourUnitList: List<TourUnit>,
    onNavToDetail: (TourUnit) -> Unit
) {
    if (tourUnitList.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small1)
        ) {

            items(tourUnitList) { tourUnit ->
                TourProgramItem(
                    tourUnit,
                    modifier = Modifier.clickable {
                        onNavToDetail(tourUnit)
                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.empty),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Spacer(Modifier.height(15.dp))
            Text(
                text = "Oops, không tìm được tour nào phù hợp.",
                style = TextStyle18,
                modifier = Modifier.alpha(0.6f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifiedSection(
    inputDate: String,
    onDateChange: (String) -> Unit,
    inputDestination: String,
    onValueChange: (String) -> Unit,
    isFiltered: Boolean,
    onFilterChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    maxPrice: Double,
    minPrice: Double,
    onPriceChange: (Pair<Double, Double>) -> Unit
) {

    val isDarkMode = isSystemInDarkTheme()

    var showDatePicker by remember { mutableStateOf(false) }

    // Convert inputDate to LocalDate or use today as default
    val selectedDate = remember(inputDate) {
        runCatching { LocalDate.parse(inputDate) }.getOrElse { LocalDate.now() }
    }

    // Formatter for yyyy-MM-dd
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val sheetState = rememberModalBottomSheetState()

    Surface {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = inputDestination,
                onValueChange = onValueChange,
                placeholder = {
                    Text(
                        text = "Nhập tour muốn tìm",
                        modifier = Modifier.alpha(0.4f)
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .shadow(elevation = 10.dp, shape = MaterialTheme.shapes.medium)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraLarge)
                        .border(1.dp, Color.LightGray, MaterialTheme.shapes.extraLarge)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = inputDate.ifBlank { "Ngày" },
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                Crossfade(targetState = isFiltered, label = "") { filtered ->
                    IconButton(onClick = { onFilterChange(!filtered) }) {
                        Icon(
                            painter = if (filtered) {
                                if(isDarkMode) painterResource(R.drawable.whitefilledfilter) else painterResource(R.drawable.filledfilter)
                            }
                            else {
                                if(isDarkMode) painterResource(R.drawable.whitefilter) else painterResource(R.drawable.filter)
                            },
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .let { if (!filtered) it.alpha(0.4f) else it }
                        )
                    }
                }
            }
        }

        // Show Material3 DatePicker inside Dialog
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                    }) {
                        Text("Xác nhận")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        onDateChange("")
                    }) {
                        Text("Hủy")
                    }
                }
            ) {
                val state = rememberDatePickerState(
                    initialSelectedDateMillis = selectedDate.toEpochDay() * 24 * 60 * 60 * 1000
                )
                DatePicker(state = state)
                LaunchedEffect(state.selectedDateMillis) {
                    state.selectedDateMillis?.let { millis ->
                        val date = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                        onDateChange(date.format(dateFormatter))
                    }
                }
            }
        }

        //show sheet
        if (isFiltered) {
            DraggableSheet(
                sheetState,
                onDismiss = {
                    onFilterChange(false)
                },
                minPrice = minPrice,
                maxPrice = maxPrice,
                onPriceChange = {
                    onPriceChange(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraggableSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    minPrice: Double,
    maxPrice: Double,
    onPriceChange: (Pair<Double, Double>) -> Unit
) {

    var min by remember {
        mutableStateOf(minPrice)
    }

    var max by remember {
        mutableStateOf(maxPrice)
    }

    var sliderPosition by remember {
        mutableStateOf(0f..5000000f)
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier.wrapContentSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.small1),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Icon(
                    painter = painterResource(R.drawable.uncheck),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            onDismiss()
                        }
                )
                Text(
                    text = "Lọc",
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.size(25.dp))
            }
            Spacer(Modifier.height(MaterialTheme.dimens.small1))
            HorizontalDivider(
                Modifier
                    .fillMaxWidth()
                    .alpha(0.3f),
                1.dp,
                Color.Black
            )
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.small1)
            ) {
                Text(
                    text = "Giá",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle17
                )
                Spacer(Modifier.height(MaterialTheme.dimens.small2))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "đ",
                        textDecoration = TextDecoration.Underline,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = Calculation.formatDouble(minPrice),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "-",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "đ",
                        textDecoration = TextDecoration.Underline,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = Calculation.formatDouble(maxPrice),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(Modifier.height(MaterialTheme.dimens.small2))
                RangeSlider(
                    value = sliderPosition,
                    steps = 0,
                    onValueChange = { range ->
                        val roundedStart = (range.start / 10000).toInt() * 10000f
                        val roundedEnd = (range.endInclusive / 10000).toInt() * 10000f
                        sliderPosition = roundedStart..roundedEnd
                    },
                    valueRange = 0f..5000000f,
                    onValueChangeFinished = {
                        min = sliderPosition.start.toDouble()
                        max = sliderPosition.endInclusive.toDouble()
                        onPriceChange(min to max)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "đ",
                        textDecoration = TextDecoration.Underline,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.alpha(0.5f)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = Calculation.formatDouble(minPrice),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.alpha(0.5f)
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "đ",
                        textDecoration = TextDecoration.Underline,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.alpha(0.5f)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = Calculation.formatDouble(maxPrice),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.alpha(0.5f)
                    )
                }
                Spacer(Modifier.height(MaterialTheme.dimens.small3))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            onPriceChange(0.0 to 5000000.0)
                            sliderPosition = 0f..5000000f
                        },
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Hủy")
                    }
                }
                Spacer(Modifier.height(MaterialTheme.dimens.medium2))
            }
        }
    }
}

@Composable
private fun HeaderSection() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2.2f)
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
                text = "Chọn địa điểm yêu thích",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            Text(
                text = "Rất nhiều địa điểm hấp dẫn đang chờ bạn",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }


    }
}

