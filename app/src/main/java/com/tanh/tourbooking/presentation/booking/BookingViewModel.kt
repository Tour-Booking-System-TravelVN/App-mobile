package com.tanh.tourbooking.presentation.booking

import android.app.Activity
import android.content.Context
import com.tanh.tourbooking.domain.model.Companion
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.eventbus.SubscriberExceptionContext
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.data.networking.api.CreateOrder
import com.tanh.tourbooking.domain.model.Information
import com.tanh.tourbooking.domain.model.TransactionDetail
import com.tanh.tourbooking.domain.usecase.auth.GetInformationUseCase
import com.tanh.tourbooking.domain.usecase.payment.CreatePaymentUseCase
import com.tanh.tourbooking.domain.usecase.payment.CreateZaloPaymentUseCase
import com.tanh.tourbooking.presentation.booking.item.InforCustomer
import com.tanh.tourbooking.presentation.detail_tour.BookingTourState
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.util.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.json.JSONObject
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val getInformationUseCase: GetInformationUseCase,
    private val createPaymentUseCase: CreatePaymentUseCase,
    private val createZaloPaymentUseCase: CreateZaloPaymentUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(BookingUiState())
    val state = _state.asStateFlow()


    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        Log.d("Zalo2", "Viewmodel init")
        val encodedJson = savedStateHandle.get<String>("state")
        if (!encodedJson.isNullOrBlank()) {
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
                    when (val role = information.second) {
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
        when (event) {
            is BookingEvent.AddCompanion -> {
                addCompanion(event.companion)
            }

            is BookingEvent.AddContactInformation -> {
                try {
                    _state.update { bookingUiState ->
                        bookingUiState.copy(
                            editedDob = event.dob?.takeIf { it.isNotBlank() }
                                ?: bookingUiState.editedDob,
                            editedGender = event.gender ?: bookingUiState.editedGender,
                            editedAddress = event.address?.takeIf { it.isNotBlank() }
                                ?: bookingUiState.editedAddress,
                            editedEmail = event.email?.takeIf { it.isNotBlank() }
                                ?: bookingUiState.editedEmail,
                            editedPhoneNumber = event.phoneNumber?.takeIf { it.isNotBlank() }
                                ?: bookingUiState.editedPhoneNumber,
                            editedLastName = event.lastname?.takeIf { it.isNotBlank() }
                                ?: bookingUiState.editedLastName,
                            editedFirstName = event.firstname?.takeIf { it.isNotBlank() }
                                ?: bookingUiState.editedFirstName
                        )
                    }
                    showSnackBar("Cập nhập thông tin thành công")
                } catch (e: Exception) {
                    showSnackBar("Cập nhập thất bại")
                }
            }

            is BookingEvent.MakeUrlPayment -> makeUrlPayment()
            is BookingEvent.MakeZaloPayment -> makeZaloPayment(event.context)
        }
    }

    private fun makeZaloPayment(context: Context) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }
            delay(1000)
            createZaloPaymentUseCase(
                tourState = _state.value.state,
                companions = _state.value.companions,
                customerInfo = InforCustomer(
                    firstname = _state.value.editedFirstName,
                    lastname = _state.value.editedLastName,
                    dob = _state.value.editedDob,
                    email = _state.value.editedEmail,
                    phoneNumber = _state.value.editedPhoneNumber,
                    gender = _state.value.editedGender,
                    address = _state.value.editedAddress
                )
            ).apply {
                onSuccess { transaction ->
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            transactionDetail = transaction
                        )
                    }
                    try {
                        val orderApi = CreateOrder()
                        val data: JSONObject =
                            orderApi.createOrder(_state.value.transactionDetail?.amount.toString())
                        val code: String = data.getString("return_code")
                        if (code == "1") {
                            val token: String = data.getString("zp_trans_token")
                            ZaloPaySDK.getInstance().payOrder(
                                context as Activity,
                                token,
                                "makeitsoapp://success",
                                object : PayOrderListener {
                                    override fun onPaymentSucceeded(
                                        p0: String?,
                                        p1: String?,
                                        p2: String?
                                    ) {
                                        Log.d("Zalo2", "Success")
                                    }

                                    override fun onPaymentCanceled(p0: String?, p1: String?) {
                                        Log.d("Zalo2", "Failure")
                                        sendEvent(OneTimeEvent.Navigate(Route.FAILURE_SCREEN.toString()))
                                    }

                                    override fun onPaymentError(
                                        p0: ZaloPayError?,
                                        p1: String?,
                                        p2: String?
                                    ) {
                                        Log.d("Zalo2", "Failure")
                                        sendEvent(OneTimeEvent.Navigate(Route.FAILURE_SCREEN.toString()))

                                    }
                                }
                            )
                        } else {
                            showSnackBar("Lỗi zalo")
                        }
                    } catch (e: Exception) {
                        showSnackBar("Lỗi zalo")
                    }
                }
                onError { exception ->
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            error = exception.message
                        )
                    }
                    sendEvent(
                        OneTimeEvent.ShowSnackbar(
                            message = exception.message ?: "Unknown error"
                        )
                    )
                }
            }
        }
    }

    private fun makeUrlPayment() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            delay(1000)
            createPaymentUseCase(
                tourState = _state.value.state,
                companions = _state.value.companions,
                customerInfo = InforCustomer(
                    firstname = _state.value.editedFirstName,
                    lastname = _state.value.editedLastName,
                    dob = _state.value.editedDob,
                    email = _state.value.editedEmail,
                    phoneNumber = _state.value.editedPhoneNumber,
                    gender = _state.value.editedGender,
                    address = _state.value.editedAddress
                )
            ).apply {
                onSuccess { transaction ->
                    Log.d("PAY1", transaction.toString())
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            transactionDetail = transaction
                        )
                    }
                }
                onError { exception ->
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            error = exception.message
                        )
                    }
                    sendEvent(
                        OneTimeEvent.ShowSnackbar(
                            message = exception.message ?: "Unknown error"
                        )
                    )
                }
            }
            if (_state.value.transactionDetail != null) {
                val url = _state.value.transactionDetail?.checkoutUrl
                if (url.isNullOrBlank()) {
                    sendEvent(OneTimeEvent.ShowSnackbar("Không thể tạo thanh toán"))
                } else {
                    sendEvent(OneTimeEvent.OpenLink(url))
                }
            }
        }
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

    fun onNavigateToSuccess() {
        sendEvent(OneTimeEvent.Navigate(Route.SUCCESS_SCREEN.toString() + "/${_state.value.transactionDetail?.orderCode}"))
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