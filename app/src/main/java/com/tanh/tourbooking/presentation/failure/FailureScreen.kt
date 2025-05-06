package com.tanh.tourbooking.presentation.failure

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tanh.tourbooking.R
import com.tanh.tourbooking.ui.theme.TextStyle20
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.Route

@Composable
fun FailureScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
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
            modifier = Modifier.size(250.dp)
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
                navController.navigate(Route.HOME_SCREEN.toString()) {
                    popUpTo(Route.FAILURE_SCREEN.toString()) {
                        inclusive = true
                    }
                }
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