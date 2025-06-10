package com.tanh.tourbooking.presentation.profile.password

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.presentation.profile.InputInformationState
import com.tanh.tourbooking.presentation.profile.ProfileEvent
import com.tanh.tourbooking.presentation.profile.ProfileViewModel
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.TextStyle18
import com.tanh.tourbooking.ui.theme.dimens

@Composable
fun PasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    popBackStack: () -> Unit
) {

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val infor = viewModel.inputInfo.collectAsState(initial = InputInformationState()).value

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is OneTimeEvent.Navigate -> Unit
                OneTimeEvent.PopBackStack -> popBackStack()
                is OneTimeEvent.ShowSnackbar -> Unit
                is OneTimeEvent.ShowToast -> Unit
                is OneTimeEvent.OpenLink -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(
                vertical = MaterialTheme.dimens.small1,
                horizontal = MaterialTheme.dimens.small2
            )
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    viewModel.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                )
            }
            Text(
                text = "Đổi mật khẩu",
                style = TextStyle18,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = {
                    viewModel.onEvent(ProfileEvent.ChangePassword)
                }
            ) {
                Text(
                    text = "Lưu",
                    style = TextStyle18
                )
            }
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))

        InputTextField2(
            title = "Mật khẩu cũ",
            value = infor.oldPassword.orEmpty(),
            focusManager = focusManager,
            focusRequest = focusRequester
        ) {
            viewModel.onOldPasswordChange(it)
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))

        InputTextField2(
            title = "Mật khẩu mới",
            value = infor.newPassword.orEmpty(),
            onValueChange = {
                viewModel.onNewPasswordChange(it)
            },
            isLast = true,
            focusRequest = focusRequester,
            focusManager = focusManager,
        )
        if (infor.errorPassword != null) {
            Text(
                text = infor.errorPassword,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = MaterialTheme.dimens.small3)
            )
        }

        Spacer(Modifier.height(MaterialTheme.dimens.small2))

    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun InputTextField2(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    focusRequest: FocusRequester,
    focusManager: FocusManager,
    isLast: Boolean = false,
    onValueChange: (String) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.width(MaterialTheme.dimens.small1))
        }

        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            shape = MaterialTheme.shapes.small,
            keyboardOptions = if (!isLast) KeyboardOptions.Default.copy(imeAction = ImeAction.Next) else
                KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onNext = {
                    if(!isLast) {
                        focusRequest.requestFocus()
                    }
                },
                onDone = {
                   if(isLast) {
                       focusManager.clearFocus()
                   }
                }
            ),
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequest)
        )
        Spacer(Modifier.height(MaterialTheme.dimens.small1))
    }
}

