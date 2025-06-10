package com.tanh.tourbooking.presentation.booking.item

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.R
import com.tanh.tourbooking.presentation.booking.BookingUiState
import com.tanh.tourbooking.ui.theme.TextStyle17
import com.tanh.tourbooking.ui.theme.TextStyle18
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.ui.theme.lightGray
import com.tanh.tourbooking.ui.theme.lighterGray
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactInformationSection(
    modifier: Modifier = Modifier,
    state: BookingUiState,
    confirmEditing: (InforCustomer) -> Unit
) {


    var isShowSheet by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()

    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    var firstname by remember(state) {
        mutableStateOf(state.editedFirstName)
    }

    var lastname by remember(state) {
        mutableStateOf(state.editedLastName)
    }

    var dob by remember(state) {
        mutableStateOf(state.editedDob)
    }

    var gender by remember(state) {
        mutableStateOf(state.editedGender)
    }

    val selectionOptions = listOf<String>("Nam", "Nữ")
    var selected by remember {
        mutableIntStateOf(
            if(gender) 0 else 1
        )
    }


    val editedGender by remember {
        derivedStateOf {
            selected == 0
        }
    }

    var phoneNumber by remember(state) {
        mutableStateOf(state.editedPhoneNumber)
    }

    var address by remember(state) {
        mutableStateOf(state.editedAddress)
    }

    var email by remember(state) {
        mutableStateOf(state.editedEmail)
    }



    Column(modifier = modifier
        .fillMaxWidth()
        .padding(MaterialTheme.dimens.small2)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = modifier
                    .height(16.dp)
                    .width(5.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(Modifier.width(MaterialTheme.dimens.small2))
            Text(text = "Thông tin liên lạc", style = TextStyle17)
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = "   Chúng tôi sẽ thông báo mọi thay đổi về đơn hàng cho bạn",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(MaterialTheme.dimens.small2))
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = lighterGray,
                    shape = MaterialTheme.shapes.medium
                )
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(MaterialTheme.dimens.small2)
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Họ",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    Text(
                        text = "Tên",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    Text(
                        text = "Số điện thoại",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    Text(
                        text = "Giới tính",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    Text(
                        text = "Ngày sinh",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(Modifier.width(5.dp))
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = firstname,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    Text(
                        text = lastname,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    Text(
                        text = phoneNumber,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    Text(
                        text = if (gender) "Nam" else "Nữ",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(MaterialTheme.dimens.small1))
                    Text(
                        text = dob,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            TextButton(
                onClick = {
                    isShowSheet = true
                },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Text(
                    text = "Chỉnh sửa",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }

    if (isShowSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                isShowSheet = false
            },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerLowest)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { isShowSheet = false }) {
                        Icon(
                            painter = painterResource(R.drawable.uncheck),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                    Text(
                        text = "Chỉnh sửa thông tin liên lạc",
                        style = TextStyle18,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.size(25.dp))
                }

                Spacer(Modifier.height(8.dp))
                HorizontalDivider(Modifier.fillMaxWidth(), 1.dp, lighterGray)
                Spacer(Modifier.height(8.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Text(text = "Họ", style = MaterialTheme.typography.bodyMedium)
                    TextField(
                        value = firstname,
                        onValueChange = { firstname = it },
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = lightGray,
                            focusedIndicatorColor = Color.Black,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(8.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Text(text = "Tên", style = MaterialTheme.typography.bodyMedium)
                    TextField(
                        value = lastname,
                        onValueChange = { lastname = it },
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = lightGray,
                            focusedIndicatorColor = Color.Black,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(8.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Text(text = "Số điện thoại", style = MaterialTheme.typography.bodyMedium)
                    TextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = lightGray,
                            focusedIndicatorColor = Color.Black,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(8.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Text(text = "Email", style = MaterialTheme.typography.bodyMedium)
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = lightGray,
                            focusedIndicatorColor = Color.Black,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(MaterialTheme.dimens.small2)
                ) {
                    Text(text = "Giới tính", style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.width(8.dp))
                    selectionOptions.forEachIndexed { index, title ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = index == selected,
                                onClick = {
                                    selected = index
                                }
                            )
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                    }
                }
                Spacer(Modifier.height(8.dp))

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 8.dp)
                ) {
                    Text(text = "Ngày sinh", style = MaterialTheme.typography.bodyMedium)
                    OutlinedTextField(
                        value = dob,
                        onValueChange = {},
                        enabled = false,
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(Icons.Default.DateRange, contentDescription = "Chọn ngày")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDatePicker = true },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                        )
                    )
                }
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    1.dp,
                    lighterGray
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        val inforCustomer = InforCustomer(
                            firstname = firstname,
                            lastname = lastname,
                            gender = editedGender,
                            dob = dob,
                            phoneNumber = phoneNumber,
                            email = email,
                            address = address
                        )
                        confirmEditing(inforCustomer)
                        isShowSheet = false
                    },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
                ) {
                    Text(text = "Lưu", style = TextStyle18)
                }
            }
        }
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatter = SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                        dob = formatter.format(Date(millis))
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

data class InforCustomer(
    val firstname: String,
    val lastname: String,
    val dob: String,
    val email: String,
    val phoneNumber: String,
    val gender: Boolean,
    val address: String
)