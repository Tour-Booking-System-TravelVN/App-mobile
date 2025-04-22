package com.tanh.tourbooking.presentation.booking

import com.tanh.tourbooking.domain.model.Companion
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.model.Information
import com.tanh.tourbooking.domain.usecase.auth.GetInformationUseCase
import com.tanh.tourbooking.presentation.detail_tour.BookingTourState
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val getInformationUseCase: GetInformationUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(BookingUiState())
    val state = _state.asStateFlow()

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        val encodedJson = savedStateHandle.get<String>("state")
        if(!encodedJson.isNullOrBlank()) {
            var cleanJsonTour = encodedJson.replace(Regex("%(?![0-9a-fA-F]{2})"), "%25")
            cleanJsonTour = cleanJsonTour.replace("\\+", "%2B")
            cleanJsonTour = cleanJsonTour.replace(Regex("%[0-9a-fA-F](?![0-9a-fA-F])"), "%25")
            val decodedJson = URLDecoder.decode(cleanJsonTour, StandardCharsets.UTF_8.toString())

            Json.decodeFromString<BookingTourState>(decodedJson).apply {
                Log.d("BO5", this.toString())
                _state.update {
                    it.copy(state = this)
                }
            }
        } else {
            _state.update {
                it.copy(
                    error = "Lỗi lấy dữ liệu"
                )
            }
        }
        viewModelScope.launch {
            getInformationUseCase().apply {
                onSuccess { information ->
                    when(val role = information.second) {
                        is Information.Customer -> {
                            _state.update { state ->
                                state.copy(
                                    editedEmail = information.first,
                                    editedAddress = role.address ?: "",
                                    editedDob = role.dateOfBirth,
                                    editedGender = role.gender,
                                    editedLastName = role.lastname,
                                    editedFirstName = role.firstname,
                                    editedPhoneNumber = role.phoneNumber ?: ""
                                )
                            }
                        }

                        is Information.TourGuide -> Unit

                    }
                }
            }
        }
    }

    fun onEvent(event: BookingEvent) {
        when(event) {
            is BookingEvent.AddCompanion -> {
                addCompanion(event.companion)
            }
            is BookingEvent.AddContactInformation -> {
                try {
                    _state.update {
                        it.copy(
                            editedDob = event.dob?.takeIf { it.isNotBlank() } ?: it.editedDob,
                            editedGender = event.gender ?: it.editedGender,
                            editedAddress = event.address?.takeIf { it.isNotBlank() } ?: it.editedAddress,
                            editedEmail = event.email?.takeIf { it.isNotBlank() } ?: it.editedEmail,
                            editedPhoneNumber = event.phoneNumber?.takeIf { it.isNotBlank() } ?: it.editedPhoneNumber,
                            editedLastName = event.lastname?.takeIf { it.isNotBlank() } ?: it.editedLastName,
                            editedFirstName = event.firstname?.takeIf { it.isNotBlank() } ?: it.editedFirstName
                        )
                    }
                    showSnackBar("Cập nhập thông tin thành công")
                }  catch (e: Exception) {
                    showSnackBar("Cập nhập thất bại")
                }
            }
            BookingEvent.MakeAPayment -> makeAPayment()
        }
    }

    private fun makeAPayment() {

    }

    private fun addCompanion(companion: Companion) {
        _state.update {
            it.copy(
                companions = it.companions + companion
            )
        }
        showSnackBar("Thêm thành công")
    }

    fun showSnackBar(message: String) {
        sendEvent(OneTimeEvent.ShowSnackbar(message))
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }

    fun popBackStack() {
        sendEvent(OneTimeEvent.PopBackStack)
    }

}