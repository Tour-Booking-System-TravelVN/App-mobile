package com.tanh.tourbooking.presentation.booking

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.tanh.tourbooking.presentation.booking.item.BottomBar
import com.tanh.tourbooking.presentation.booking.item.CompanionSection
import com.tanh.tourbooking.presentation.booking.item.ContactInformationSection
import com.tanh.tourbooking.presentation.booking.item.RestSection
import com.tanh.tourbooking.presentation.booking.item.TourInformationSection
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.TextStyle18
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.ui.theme.lightGray
import com.tanh.tourbooking.ui.theme.lighterGray
import androidx.core.net.toUri
import com.tanh.tourbooking.domain.model.Discount
import com.tanh.tourbooking.util.Calculation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    modifier: Modifier = Modifier,
    viewModel: BookingViewModel = hiltViewModel<BookingViewModel>(),
    showSnackBar: (String) -> Unit,
    popBackStack: () -> Unit,
    onNavigate: (String) -> Unit
) {

    val state = viewModel.state.collectAsState(initial = BookingUiState()).value
    val context = LocalContext.current

    val companionSheetState = rememberModalBottomSheetState()
    var isShowCompanionBottom by remember {
        mutableStateOf(false)
    }

    var checked by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is OneTimeEvent.Navigate -> onNavigate(event.route)
                OneTimeEvent.PopBackStack -> popBackStack()
                is OneTimeEvent.ShowSnackbar -> showSnackBar(event.message)
                is OneTimeEvent.ShowToast -> Unit
                is OneTimeEvent.OpenLink -> {
                    val intent = Intent(ACTION_VIEW, event.url.toUri())
                    context.startActivity(intent)
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                totalAmount = Calculation.discountedPrice(
                    amount = state.state.totalPrice,
                    discount = state.state.discount ?: Discount(
                        discountName = "Không giảm",
                        discountUnit = "VND",
                        discountValue = 0.0,
                        id = 0
                    )
                ),
            ) {
                if (!checked) {
                    viewModel.showSnackBar("Vui lòng đồng ý điều khoản")
                } else {
                    Log.d("PAY1", "run")
                    viewModel.onEvent(BookingEvent.MakeAPayment)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        viewModel.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(25.dp)
                    )
                }
                Spacer(Modifier.width(MaterialTheme.dimens.small1))
                Text(
                    text = "Hoàn tất đơn hàng",
                    style = TextStyle18,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(Modifier.width(MaterialTheme.dimens.small1))
            HorizontalDivider(
                Modifier.fillMaxWidth(),
                thickness = 0.5.dp,
                color = lightGray
            )
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
            //tour information section
            TourInformationSection(state = state)
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                10.dp,
                lighterGray
            )
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
            //companion section
            CompanionSection(
                sheetState = companionSheetState,
                isShowBottom = isShowCompanionBottom,
                onDismissRequest = { isShowCompanionBottom = false },
                state = state,
                showSheet = { isShowCompanionBottom = true }
            ) { companion ->
                viewModel.onEvent(BookingEvent.AddCompanion(companion))
                isShowCompanionBottom = false
            }

            Spacer(Modifier.height(MaterialTheme.dimens.small2))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                10.dp,
                lighterGray
            )
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
            //contact section
            ContactInformationSection(
                state = state,
                confirmEditing = {
                    viewModel.onEvent(
                        BookingEvent.AddContactInformation(
                            firstname = it.firstname,
                            lastname = it.lastname,
                            dob = it.dob,
                            phoneNumber = it.phoneNumber,
                            email = it.email,
                            address = it.address,
                            gender = it.gender
                        )
                    )
                }
            )
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                10.dp,
                lighterGray
            )
            Spacer(Modifier.height(MaterialTheme.dimens.small2))
            RestSection(
                state = state,
                checked = checked,
                onCheckChange = {
                    checked = !checked
                }
            )
        }
        if (state.isLoading) {
            Dialog(
                onDismissRequest = {}
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text("Đang tiến hành thanh toán")
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewABC(modifier: Modifier = Modifier) {
    TourBookingTheme {
        BookingScreen(
            showSnackBar = {},
            onNavigate = {},
            popBackStack = {},
            viewModel = hiltViewModel()
        )
    }
}