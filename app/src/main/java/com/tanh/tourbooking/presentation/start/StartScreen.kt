package com.tanh.tourbooking.presentation.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.R
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.Route

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {

    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val width = maxWidth
        val height = maxHeight

        Image(
            painter = painterResource(id = R.drawable.expedition),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 24.dp).align(Alignment.BottomCenter)
        ) {
            Button(
                onClick = {
                    onNavigate(Route.LOGIN_SCREEN.toString())
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(60.dp)
            ) {
                Text(
                    text = "Get Started  →",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W500
                )
            }
        }

        //logo
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Image(
                painter = painterResource(R.drawable.logo_tour),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(120.dp)
                    .padding(MaterialTheme.dimens.small2)
            )
//            Text(
//                text = "TravelVN",
//                style = MaterialTheme.typography.headlineMedium,
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colorScheme.secondary
//            )
        }
    }

}