package com.tanh.tourbooking.presentation.detail_tour.item

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tanh.tourbooking.R
import com.tanh.tourbooking.data.mappers.toLocalDate
import com.tanh.tourbooking.domain.model.Discount
import com.tanh.tourbooking.domain.model.TourUnit
import com.tanh.tourbooking.presentation.detail_tour.BookingTourState
import com.tanh.tourbooking.presentation.detail_tour.CalendarUiState
import com.tanh.tourbooking.presentation.detail_tour.screen.PeopleSelectorItem
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.Calculation
import com.tanh.tourbooking.util.toFormattedDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("UnrememberedMutableState")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BottomSheet(
    modifier: Modifier = Modifier,
    calendarState: CalendarUiState,
    tourUnit: TourUnit?,
    discount: Discount?,
    bottomSheetState: SheetState,
    onSheetStateChange: (Boolean) -> Unit,
    dismissSheet: () -> Unit,
    onCalendar: () -> Unit,
    bookTour: (BookingTourState) -> Unit
) {

    var adultPrice by remember {
        mutableDoubleStateOf(0.0)
    }

    var toddlePrice by remember {
        mutableDoubleStateOf(0.0)
    }

    var childPrice by remember {
        mutableDoubleStateOf(0.0)
    }

    var babyPrice by remember {
        mutableDoubleStateOf(0.0)
    }

    var adultCount by remember {
        mutableIntStateOf(1)
    }
    var toddleCount by remember {
        mutableIntStateOf(0)
    }
    var childCount by remember {
        mutableIntStateOf(0)
    }
    var babyCount by remember {
        mutableIntStateOf(0)
    }

    var isDateChosen by remember {
        mutableStateOf(false)
    }

    var chosenDate by remember {
        mutableIntStateOf(0)
    }

    var chosenMonth by remember {
        mutableIntStateOf(0)
    }

    var discountValue by remember {
        mutableStateOf(discount?.discountName ?: "")
    }

    var bookingTourState by remember {
        mutableStateOf(BookingTourState())
    }

    LaunchedEffect(isDateChosen) {
        if(chosenMonth != 0 && chosenDate != 0) {
            val currentYear = LocalDate.now().year
            val currentDate = chosenDate.toString()
            val currentMonth = chosenMonth.toString()
            val formattedString = "$currentYear-${currentMonth.padStart(2, '0')}-$currentDate".toFormattedDate()
            val chosenLocalDate = formattedString.toLocalDate()

            val tourUnitCalendar = calendarState.calendar
                .map { it.second }
                .find { it.departureDate == chosenLocalDate }
            Log.d("BO3", tourUnitCalendar.toString())

            if (tourUnitCalendar != null) {
                adultPrice = tourUnitCalendar.adultTourPrice
                toddlePrice = tourUnitCalendar.toddlerTourPrice
                babyPrice = tourUnitCalendar.babyTourPrice
                childPrice = tourUnitCalendar.childTourPrice
                discountValue = tourUnitCalendar.discount.discountName

                bookingTourState = bookingTourState.copy(
                    departureDate = chosenLocalDate,
                    adultPrice = tourUnitCalendar.adultTourPrice,
                    toddlePrice = tourUnitCalendar.toddlerTourPrice,
                    babyPrice = tourUnitCalendar.babyTourPrice,
                    childPrice = tourUnitCalendar.childTourPrice,
                    tourUnitId = tourUnitCalendar.tourUnitId,
                    discount = tourUnitCalendar.discount,
                    roomPrice = tourUnitCalendar.privateRoomPrice,
                    tourName = tourUnit?.tour?.tourName ?: ""
                )
            }
        }
    }

    val totalAmount by derivedStateOf {
        (adultCount * adultPrice) +
                (childCount * childPrice) +
                (babyCount * babyPrice) +
                (toddleCount * toddlePrice)
    }

    var isShowDialog by remember {
        mutableStateOf(false)
    }

    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = {
            onSheetStateChange(false)
        },
        dragHandle = null,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Spacer(Modifier.height(MaterialTheme.dimens.small1))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        dismissSheet()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.uncheck),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "Tùy chọn đơn hàng",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 0.5.dp,
                color = Color.LightGray,
            )
            //number
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

            //name
            Text(
                text = tourUnit?.tour?.tourName ?: "",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small2)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))

            //infor
            Row(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InforItem(content = "Hủy miễn phí 24 giờ")
                Spacer(Modifier.width(3.dp))
                InforItem(content = "Xác nhận tức thời")
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 0.5.dp,
                color = Color.LightGray,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

            //calendar selection
            val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM"))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
            ) {
                Text("""Vui lòng chọn ngày 
                    |khởi hành""".trimMargin())
                Spacer(Modifier.weight(1f))
                Text(
                    text = if(chosenDate == 0 && chosenMonth == 0) "$currentDate - 31/12" else "$chosenDate/$chosenMonth/2025",
                    modifier = Modifier.clickable {
                        isShowDialog = true
                        onCalendar()
                    }
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 0.5.dp,
                color = Color.LightGray,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = MaterialTheme.dimens.small2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Giá phòng"
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "đ",
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = Calculation.formatDouble(bookingTourState.roomPrice),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.width(6.dp))
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            HorizontalDivider(
                thickness = 0.5.dp,
                color = Color.LightGray,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            PeopleSelectorItem(
                title = "Người lớn",
                isAdult = true,
                count = adultCount,
                price = adultPrice,
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
                title = "Trẻ em",
                count = childCount,
                price = childPrice,
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
                title = "Trẻ em (1 - 3)",
                count = toddleCount,
                price = toddlePrice,
                onValueChange = {
                    toddleCount = it
                }
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            HorizontalDivider(
                thickness = 0.5.dp,
                color = Color.LightGray,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            PeopleSelectorItem(
                title = "Em bé",
                count = babyCount,
                price = babyPrice,
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
        //total amount
        Row(
            modifier = Modifier.padding(
                vertical = MaterialTheme.dimens.small1,
                horizontal = MaterialTheme.dimens.small2
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "đ",
                style = MaterialTheme.typography.titleLarge,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = Calculation.formatDouble(totalAmount + bookingTourState.roomPrice),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.weight(1f))
            if(discount != null) {
                Text(
                    text = discountValue,
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = TextDecoration.LineThrough,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {
                    adultCount = 1
                    childCount = 0
                    babyCount = 0
                },
                shape = MaterialTheme.shapes.small,
                contentPadding = PaddingValues(MaterialTheme.dimens.small2)
            ) {
                Text(
                    text = "Làm mới"
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small2))
            Button(
                onClick = {
                    if(isDateChosen) {
                        bookingTourState = bookingTourState.copy(
                            totalPrice = totalAmount,
                            adultNumber = adultCount,
                            toddleNumber = toddleCount,
                            childNumber = childCount,
                            babyNumber = babyCount
                        )
                        dismissSheet()
                        Log.d("BO3", "bookState: ${bookingTourState.toString()}")
                        bookTour(bookingTourState)
                    }
                },
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                contentPadding = PaddingValues(MaterialTheme.dimens.small2)
            ) {
                Text(
                    text = "Xác nhận"
                )
            }
        }
    }
    if(isShowDialog) {
        Dialog(
            onDismissRequest = {
                isShowDialog = false
            }
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp)
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
            ) {
                CalendarSection(
                    months = calendarState.months,
                    isDateChosen = { date, month ->
                        Log.d("CAL6", "$date $month")
                        chosenDate = date
                        chosenMonth = month
                        isDateChosen = true
                        isShowDialog = false

                    }
                )
            }
        }
    }
}

@Composable
fun InforItem(
    modifier: Modifier = Modifier,
    content: String
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(2.dp)
    ) {
        Text(
            text = content,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

