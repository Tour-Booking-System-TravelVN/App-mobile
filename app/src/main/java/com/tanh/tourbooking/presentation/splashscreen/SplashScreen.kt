package com.tanh.tourbooking.presentation.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tanh.tourbooking.R
import com.tanh.tourbooking.util.Route
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNav: () -> Unit
) {

    LaunchedEffect(Unit) {
        delay(1500L)
        onNav()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_tour),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
    }

}