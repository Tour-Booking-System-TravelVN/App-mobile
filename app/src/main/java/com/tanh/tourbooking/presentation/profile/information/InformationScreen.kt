package com.tanh.tourbooking.presentation.profile.information

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tanh.tourbooking.presentation.profile.InputInformationState
import com.tanh.tourbooking.presentation.profile.ProfileEvent
import com.tanh.tourbooking.presentation.profile.ProfileViewModel
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.ui.theme.TextStyle18
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import com.tanh.tourbooking.ui.theme.dimens
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    onNavigate: (String) -> Unit,
    popBackStack: () -> Unit,
    showSnackBar: (String) -> Unit
) {

    val infor = viewModel.inputInfo.collectAsState(initial = InputInformationState()).value

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

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(
                vertical = MaterialTheme.dimens.small1,
                horizontal = MaterialTheme.dimens.small2
            ).verticalScroll(rememberScrollState()),
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
                    modifier = Modifier.size(25.dp),
                )
            }
            Text(
                text = "Thông tin cá nhân",
                style = TextStyle18,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = {
                    viewModel.onEvent(ProfileEvent.SaveEditedInformation)
                }
            ) {
                Text(
                    text = "Lưu",
                    style = TextStyle18
                )
            }
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))

        InputTextField(
            title = "Họ",
            value = infor.firstname.orEmpty(),
            error = infor.firstnameError
        ) {
            viewModel.onFirstnameChange(it)
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))

        InputTextField(
            title = "Tên",
            value = infor.lastname.orEmpty(),
            error = infor.lastnameError
        ) {
            viewModel.onLastnameChange(it)
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))

        //gender
        Text(
            text = "Giới tính",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = infor.gender == true,
                onClick = { viewModel.onGenderChange(true) }
            )
            Text(text = "Nam")

            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small2))

            RadioButton(
                selected = infor.gender == false,
                onClick = { viewModel.onGenderChange(false) }
            )
            Text(text = "Nữ")
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))
        //dob
        Text(
            text = "Ngày sinh",
            style = MaterialTheme.typography.bodyLarge
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true }
        ) {
            OutlinedTextField(
                value = infor.dateOfBirth.orEmpty(),
                onValueChange = {},
                readOnly = true,
                enabled = false ,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(MaterialTheme.dimens.small2))
        InputTextField(
            title = "Số điện thoại",
            value = infor.phoneNumber.orEmpty(),
            error = infor.phoneNumberError
        ) {
            viewModel.onPhoneNumberChange(it)
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))

        InputTextField(
            title = "Email",
            value = infor.email.orEmpty(),
            error = infor.emailError
        ) {
            viewModel.onEmailChange(it)
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))

        InputTextField(
            title = "Số CCCD",
            value = infor.citizenId.orEmpty(),
            error = infor.citizenIdError
        ) {
            viewModel.onCitizenIdChange(it)
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))

        InputTextField(
            title = "Địa chỉ",
            value = infor.address.orEmpty(),
            error = infor.addressError
        ) {
            viewModel.onAddressChange(it)
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))

        InputTextField(
            title = "Quốc tịch",
            value = infor.nationality.orEmpty(),
            error = infor.nationalityError
        ) {
            viewModel.onNationalityChange(it)
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))

        InputTextField(
            title = "Số hộ chiếu",
            value = infor.passport.orEmpty(),
            error = infor.passportError
        ) {
            viewModel.onPassportChange(it)
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))

        InputTextField(
            title = "Ghi chú",
            value = infor.note.orEmpty(),
            error = null
        ) {
            viewModel.onNoteChange(it)
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val formattedDate = formatter.format(Date(millis))
                            viewModel.onDateOfBirthChange(formattedDate)
                        }
                        showDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Hủy")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    error: String? = null,
    onValueChange: (String) -> Unit
) {

    val isError by derivedStateOf {
        error != null
    }

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
            if(isError) {
                Text(
                    text = error ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        Spacer(Modifier.height(MaterialTheme.dimens.small1))
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            isError = isError,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

