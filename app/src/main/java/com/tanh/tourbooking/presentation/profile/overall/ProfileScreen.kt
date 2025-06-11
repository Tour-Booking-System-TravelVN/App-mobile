package com.tanh.tourbooking.presentation.profile.overall

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Blind
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tanh.tourbooking.R
import com.tanh.tourbooking.presentation.profile.ProfileEvent
import com.tanh.tourbooking.presentation.profile.ProfileUiState
import com.tanh.tourbooking.presentation.profile.ProfileViewModel
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.Role
import com.tanh.tourbooking.util.Route

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    onNavigate: (String) -> Unit,
    showSnackBar: (String) -> Unit,
    popBackStack: () -> Unit
) {

    val state = viewModel.state.collectAsState(initial = ProfileUiState()).value

    val isCustomer by derivedStateOf {
        state.role == Role.CUSTOMER
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.scenerylottie))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever, isPlaying = true)

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

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {

        LottieAnimation(
            composition = composition,
            progress = {progress},
            modifier = Modifier.fillMaxWidth().height(200.dp)
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .align(Alignment.TopCenter)
        )

        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.small3)
                .align(Alignment.BottomCenter)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(8.dp)
                    .padding(horizontal = MaterialTheme.dimens.small2)
            ) {
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
                Text(
                    text = if (isCustomer) state.customer?.firstname + " " + state.customer?.lastname else
                        state.tourGuide?.firstname + " " + state.tourGuide?.lastname,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium1))
                profileList.fastForEach { profile ->
                    ProfileItem(
                        icon = profile.second, title = profile.first,
                        modifier = Modifier.clickable {
                            if(profile.first == "Đăng xuất") {
                                viewModel.onEvent(ProfileEvent.Logout)
                            }
                            else if(profile.first == "Thông tin cá nhân") {
                                viewModel.onEvent(ProfileEvent.onNavToScreen(Route.INFOR_SCREEN.toString()))
                            }
                            else if(profile.first == "Đổi mật khẩu") {
                                viewModel.onEvent(ProfileEvent.onNavToScreen(Route.CHGPWD_SCREEN.toString()))
                            }
                            else if(profile.first == "Tin nhắn") {
                                viewModel.onEvent(ProfileEvent.onNavToScreen(Route.CHATS_SCREEN.toString()))
                            } else {
                                viewModel.onEvent(ProfileEvent.onNavToScreen(Route.MY_TOURS_SCREEN.toString()))
                            }
                        })
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
                }
            }
        }
    }

}

@Composable
fun ProfileItem(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimens.small2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(0.dp)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.small2))
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

val profileList = listOf(
    "Thông tin cá nhân" to Icons.Default.Person,
    "Tin nhắn" to Icons.Default.MailOutline,
    "Hoạt động" to Icons.Default.Blind,
    "Đổi mật khẩu" to Icons.Default.Settings,
    "Đăng xuất" to Icons.Default.Logout
)