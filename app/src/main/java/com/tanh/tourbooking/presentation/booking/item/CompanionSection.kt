package com.tanh.tourbooking.presentation.booking.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.R
import com.tanh.tourbooking.domain.model.Companion
import com.tanh.tourbooking.presentation.booking.BookingUiState
import com.tanh.tourbooking.ui.theme.TextStyle17
import com.tanh.tourbooking.ui.theme.TextStyle18
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.ui.theme.lightGray
import com.tanh.tourbooking.ui.theme.lighterGray
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CompanionSection(
    modifier: Modifier = Modifier,
    state: BookingUiState,
    isShowBottom: Boolean,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    showSheet: () -> Unit,
    addCompanion: (Companion) -> Unit
) {
    val options = listOf("Nam", "Nữ")
    val selectedOption = remember { mutableStateOf(options[0]) }

    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    val gender by remember { derivedStateOf { selectedOption.value == "Nam" } }

    var dob by remember { mutableStateOf("") }
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.small2)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = modifier
                    .height(16.dp)
                    .width(5.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(Modifier.width(MaterialTheme.dimens.small2))
            Text(text = "Thông tin người tham gia", style = TextStyle17)
        }
        Spacer(Modifier.height(MaterialTheme.dimens.small2))
        FlowRow(

        ) {
            repeat(state.companions.size) { index ->
                val companion = state.companions[index]
                OutlinedButton(
                    onClick = {},
                    shape = MaterialTheme.shapes.small,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "${companion.firstName} ${companion.lastName}",
                        style = TextStyle17,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(Modifier.width(8.dp))
            }
            OutlinedButton(
                onClick = { showSheet() },
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "+  Thêm",
                    style = TextStyle17,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    if (isShowBottom) {
        ModalBottomSheet(
            onDismissRequest = { onDismissRequest() },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.background(Color.White)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { onDismissRequest() }) {
                        Icon(
                            painter = painterResource(R.drawable.uncheck),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                    Text(
                        text = "Thêm người tham gia",
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

                Column(Modifier
                    .fillMaxWidth()
                    .padding(15.dp)) {
                    Text(text = "Họ", style = MaterialTheme.typography.bodyMedium)
                    TextField(
                        value = firstname,
                        onValueChange = { firstname = it },
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = lightGray,
                            focusedIndicatorColor = Color.Black,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(10.dp))

                Column(Modifier
                    .fillMaxWidth()
                    .padding(15.dp)) {
                    Text(text = "Tên", style = MaterialTheme.typography.bodyMedium)
                    TextField(
                        value = lastname,
                        onValueChange = { lastname = it },
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = lightGray,
                            focusedIndicatorColor = Color.Black,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)) {
                    Text(text = "Giới tính", style = MaterialTheme.typography.bodyMedium)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        options.forEach { option ->
                            RadioButton(
                                selected = selectedOption.value == option,
                                onClick = { selectedOption.value = option }
                            )
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                            Spacer(Modifier.width(10.dp))
                        }
                    }
                }

                Column(Modifier
                    .fillMaxWidth()
                    .padding(15.dp)) {
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
                            disabledTextColor = Color.Black,
                            disabledTrailingIconColor = Color.Black,
                            disabledContainerColor = Color.White
                        )
                    )
                }

                Button(
                    onClick = {
                        val companion = Companion(
                            firstName = firstname,
                            lastName = lastname,
                            dob = dob,
                            gender = gender
                        )
                        addCompanion(companion)
                    },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
                ) {
                    Text(text = "Lưu", style = TextStyle18)
                }
            }
        }
    }

    // Date Picker Dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            dob = formatter.format(Date(millis))
                        }
                        showDatePicker = false
                    }
                ) {
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
