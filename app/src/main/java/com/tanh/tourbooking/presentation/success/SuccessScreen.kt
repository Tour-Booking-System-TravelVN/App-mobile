package com.tanh.tourbooking.presentation.success

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tanh.tourbooking.R
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.TextStyle18
import com.tanh.tourbooking.ui.theme.TextStyle20
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.Route

@Composable
fun SuccessScreen(
    modifier: Modifier = Modifier,
    orderCode: String?,
    viewModel: SuccessViewModel = hiltViewModel<SuccessViewModel>(),
    onNavigate: (String) -> Unit
) {

    val state = viewModel.state.collectAsState().value

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

    if(state.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        if(state.error == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(
                        horizontal = MaterialTheme.dimens.small2,
                        vertical = MaterialTheme.dimens.medium3
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.successfull),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
                Spacer(Modifier.height(MaterialTheme.dimens.small3))
                Text(
                    text = "Thanh toán thành công",
                    style = TextStyle20,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.alpha(0.8f)
                )
                Spacer(Modifier.height(MaterialTheme.dimens.small1))
                Text(
                    text = "Mã đơn hàng: ${state.bookingId}",
                    style = TextStyle20,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.alpha(0.8f)
                )
                TextButton(
                    onClick = {
                        viewModel.navToRoute(Route.MY_TOURS_SCREEN.toString())
                    }
                ) {
                    Text(
                        text = "Xem chi tiết đơn hàng",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(
                        horizontal = MaterialTheme.dimens.small2,
                        vertical = MaterialTheme.dimens.medium3
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.cancelpayment),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
                Spacer(Modifier.height(MaterialTheme.dimens.small3))
                Text(
                    text = "Thanh toán không thành công",
                    style = TextStyle20,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.alpha(0.8f)
                )
                Spacer(Modifier.height(MaterialTheme.dimens.small1))
                TextButton(
                    onClick = {
                        viewModel.navToRoute(Route.HOME_SCREEN.toString())
                    }
                ) {
                    Text(
                        text = "Quay trở về trang chủ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }

}