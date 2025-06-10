package com.tanh.tourbooking.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tanh.tourbooking.R
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.Route

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>(),
    modifier: Modifier = Modifier,
    showSnackBar: (String) -> Unit,
    onNavigate: (String) -> Unit
) {

    val state = viewModel.state.collectAsState(initial = LoginState()).value
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(true) {
        viewModel.channel.collect { event ->
            when(event) {
                is OneTimeEvent.Navigate -> {
                    onNavigate(event.route)
                }
                OneTimeEvent.PopBackStack -> {}
                is OneTimeEvent.ShowSnackbar -> {
                    showSnackBar(event.message)
                }
                is OneTimeEvent.ShowToast -> Unit
                is OneTimeEvent.OpenLink -> Unit
            }
        }
    }

    var inputName by remember {
        mutableStateOf("")
    }

    var inputPassword by remember {
        mutableStateOf("")
    }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(horizontal = MaterialTheme.dimens.medium1),
        verticalArrangement = Arrangement.Center,
    ) {
        //Logohhhbv
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.logo_tour_no_text),
                contentDescription = null,
                modifier = Modifier.size(MaterialTheme.dimens.large)
            )
            Text(
                text = "TravelVN",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.W500
            )
        }
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.small3))
        Text(
            text = "Welcome Travelling",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.medium1))

        //name text field
        OutlinedTextField(
            value = inputName,
            onValueChange = {
                inputName = it
            },
            label = {
                Text("Tên đăng nhập")
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester.requestFocus()
                }
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.small2))
        OutlinedTextField(
            value = inputPassword,
            onValueChange = {
                inputPassword = it
            },
            label = {
                Text("Mật khẩu")
            },
            visualTransformation = if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible }
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.inverseSurface
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.small2))
        Text(
            text = "Quên mật khẩu?",
            color = Color.LightGray,
            textDecoration = TextDecoration.Underline,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.small3))
        Button(
            onClick = {
                viewModel.onEvent(LoginEvent.Login(inputName, inputPassword))
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
        ) {
            Text(
                "Đăng nhập"
            )
        }
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.medium1))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                thickness = 0.5.dp,
                color = Color.LightGray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "OR",
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            HorizontalDivider(
                thickness = 0.5.dp,
                color = Color.LightGray,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.small3))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Không có tài khoản?",
                style = MaterialTheme.typography.bodyMedium
            )
            TextButton(
                onClick = {
                    viewModel.onEvent(LoginEvent.NavToRegister(Route.REGISTER_SCREEN.toString()))
                }
            ) {
                Text(
                    text = "Đăng ký ngay",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}

@Preview
@Composable
fun PreviewLoginScreen(
    modifier: Modifier = Modifier
) {
    TourBookingTheme {
//        LoginScreen()
    }
}