package com.tanh.tourbooking.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tanh.tourbooking.R
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.util.Route

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel<RegisterViewModel>(),
    showSnackbar: (String) -> Unit,
    onNavigate: (String) -> Unit
) {

    val state = viewModel.state.collectAsState(initial = RegisterState()).value

    LaunchedEffect(true) {
        viewModel.channel.collect { event ->
            when(event) {
                is OneTimeEvent.Navigate -> {
                    onNavigate(event.route)
                }
                OneTimeEvent.PopBackStack -> {}
                is OneTimeEvent.ShowSnackbar -> {
                    showSnackbar(event.message)
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

    var inputEmail by remember {
        mutableStateOf("")
    }

    var inputConfirmPassword by remember {
        mutableStateOf("")
    }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    var isConfirmPasswordVisible by remember {
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
            text = "Đăng ký tài khoản",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.medium1))

        //name text field
        OutlinedTextField(
            value = inputName,
            onValueChange = {
                inputName = it
                viewModel.onUsernameChange(it)
            },
            label = {
                Text("Tên đăng nhập")
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.small2))
        //password
        OutlinedTextField(
            value = inputPassword,
            onValueChange = {
                inputPassword = it
                viewModel.onPassword(it)
            },
            label = {
                Text("Nhập mật khẩu")
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
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.small2))
        //confirmpassword
        OutlinedTextField(
            value = inputConfirmPassword,
            onValueChange = {
                inputConfirmPassword = it
                viewModel.onConfirmPassword(it)
            },
            label = {
                Text("Nhập lại mật khẩu")
            },
            visualTransformation = if (isConfirmPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }
                ) {
                    Icon(
                        imageVector = if (isConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.inverseSurface
                    )
                }
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.small2))
        //email
        OutlinedTextField(
            value = inputEmail,
            onValueChange = {
                inputEmail = it
                viewModel.onEmailChange(it)
            },
            label = {
                Text("Email")
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.small3))
        Button(
            onClick = {
                viewModel.onEvent(RegisterEvent.RegisterAccount)
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
                "Đăng ký"
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
                text = "Đã có tài khoản?",
                style = MaterialTheme.typography.bodyMedium
            )
            TextButton(
                onClick = {
                    viewModel.onEvent(RegisterEvent.OnNavToLogin(Route.LOGIN_SCREEN.toString()))
                }
            ) {
                Text(
                    text = "Đăng nhập ngay",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}