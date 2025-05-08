package com.tanh.tourbooking.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.request.UpdateInfo
import com.tanh.tourbooking.data.model.request.UpdateInfoCustomerRequest
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.model.Information
import com.tanh.tourbooking.domain.usecase.auth.ChangePasswordInfoUseCase
import com.tanh.tourbooking.domain.usecase.auth.CheckRoleUseCase
import com.tanh.tourbooking.domain.usecase.auth.GetInformationUseCase
import com.tanh.tourbooking.domain.usecase.auth.LogoutUseCase
import com.tanh.tourbooking.domain.usecase.auth.UpdateInforCustomerUseCase
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.util.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val checkRoleUseCase: CheckRoleUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getInformationUseCase: GetInformationUseCase,
    private val updateInforCustomerUseCase: UpdateInforCustomerUseCase,
    private val changePasswordInfoUseCase: ChangePasswordInfoUseCase
): ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    private val _inputInfor = MutableStateFlow(InputInformationState())
    val inputInfo = _inputInfor.asStateFlow()

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            launch {
                getInformation()
            }
            launch {
                getRole()
            }
        }
    }

    private suspend fun getRole() {
        _state.update {
            it.copy(
                role = checkRoleUseCase()
            )
        }
    }

    private suspend fun getInformation() {
        _state.value = _state.value.copy(isLoading = true)
        getInformationUseCase().apply {
            onSuccess { information ->
                when (val infor = information.second) {
                    is Information.Customer -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                customer = infor,
                            )
                        }
                        _inputInfor.update {
                            it.copy(
                                address = infor.address,
                                citizenId = infor.citizenId,
                                dateOfBirth = infor.dateOfBirth,
                                firstname = infor.firstname,
                                gender = infor.gender,
                                lastname = infor.lastname,
                                nationality = infor.nationality,
                                note = infor.note,
                                passport = infor.passport,
                                phoneNumber = infor.phoneNumber,
                                email = information.first
                            )
                        }
                    }

                    is Information.TourGuide -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                tourGuide = infor
                            )
                        }
                    }
                }
            }
            onError { exception ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = exception.message
                    )
                }
                showSnackBar(exception.message ?: "Unknown error")
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.onNavToScreen -> onNavToPrivateInformation(event.route)
            ProfileEvent.Logout -> logout()
            ProfileEvent.SaveEditedInformation -> saveInfor()
            ProfileEvent.ChangePassword -> changePassword()
        }
    }

    private fun changePassword() {
        viewModelScope.launch {
            val oldPassword = _inputInfor.value.oldPassword
            val newPassword = _inputInfor.value.newPassword
            if(oldPassword.isNullOrBlank() || newPassword.isNullOrBlank()) {
                _inputInfor.update { state ->
                    state.copy(
                        errorPassword = "Vui lòng nhập đủ thông tin"
                    )
                }
                return@launch
            }
            changePasswordInfoUseCase(
                oldPassword = oldPassword,
                newPassword = newPassword
            ).apply {
                onSuccess {
                    showSnackBar("Đổi mật khẩu thành công")
                    _inputInfor.update { state ->
                        state.copy(
                            oldPassword = null,
                            newPassword = null,
                            errorPassword = null
                        )
                    }
                }
                onError { e ->
                    _inputInfor.update { state ->
                        state.copy(
                            errorPassword = e.message
                        )
                    }
                }
            }
        }
    }

    private fun saveInfor() {
        viewModelScope.launch {
            if(!checkFirstname() || !checkLastname() || !checkPhoneNumber() || !checkEmail()) {
                return@launch
            }
            val request = UpdateInfoCustomerRequest(
                email = _inputInfor.value.email,
                customer = UpdateInfo(
                    address = _inputInfor.value.address,
                    citizenId = _inputInfor.value.citizenId,
                    dateOfBirth = _inputInfor.value.dateOfBirth,
                    firstname = _inputInfor.value.firstname,
                    gender = _inputInfor.value.gender,
                    lastname = _inputInfor.value.lastname,
                    nationality = _inputInfor.value.nationality,
                    note = _inputInfor.value.note,
                    passport = _inputInfor.value.passport,
                    phoneNumber = _inputInfor.value.phoneNumber
                )
            )
            updateInforCustomerUseCase(request).apply {
                onSuccess {
                    showSnackBar("Cập nhập thông tin thành công")
                }
                onError {
                    showSnackBar("Cập nhập thông tin thất bại")
                }
            }
        }
    }

    fun checkFirstname(): Boolean {
        val firstname = _inputInfor.value.firstname?.trim()
        if(firstname.isNullOrBlank()) {
            _inputInfor.update { state ->
                state.copy(firstnameError = "Vui lòng nhập thông tin")
            }
            return false
        }
        return true
    }

    fun checkLastname(): Boolean {
        val lastname = _inputInfor.value.lastname?.trim()
        if(lastname.isNullOrBlank()) {
            _inputInfor.update { state ->
                state.copy(lastnameError = "Vui lòng nhập thông tin")
            }
            return false
        }
        return true
    }

    fun checkEmail(): Boolean {
        val email = _inputInfor.value.email?.trim()
        val regexPattern = Regex("^\\w+@gmail.com")
        if(email.isNullOrBlank()) {
            _inputInfor.update { state ->
                state.copy(emailError = "Vui lòng nhập thông tin")
            }
            return false
        } else if(!regexPattern.matches(email)) {
            _inputInfor.update { state ->
                state.copy(emailError = "Sai định dạng gmail")
            }
            return false
        }
        return true
    }

    fun checkPhoneNumber(): Boolean {
        val phoneNumber = _inputInfor.value.phoneNumber?.trim()
        val regexPattern = Regex("^\\d{10}$")
        if(phoneNumber.isNullOrBlank()) {
            _inputInfor.update { state ->
                state.copy(phoneNumberError = "Vui lòng nhập thông tin")
            }
            return false
        } else if(!regexPattern.matches(phoneNumber)) {
            _inputInfor.update { state ->
                state.copy(phoneNumberError = "Vui lòng nhập số, 10 kí tự")
            }
            return false
        }
        return true
    }



    fun onFirstnameChange(firstname: String) {
        _inputInfor.update { it.copy(firstname = firstname) }
    }

    fun onLastnameChange(lastname: String) {
        _inputInfor.update { it.copy(lastname = lastname) }
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _inputInfor.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun onCitizenIdChange(citizenId: String) {
        _inputInfor.update { it.copy(citizenId = citizenId) }
    }

    fun onAddressChange(address: String) {
        _inputInfor.update { it.copy(address = address) }
    }

    fun onNationalityChange(nationality: String) {
        _inputInfor.update { it.copy(nationality = nationality) }
    }

    fun onPassportChange(passport: String) {
        _inputInfor.update { it.copy(passport = passport) }
    }

    fun onNoteChange(note: String) {
        _inputInfor.update { it.copy(note = note) }
    }

    fun onEmailChange(email: String) {
        _inputInfor.update { it.copy(email = email) }
    }

    fun onDateOfBirthChange(dob: String) {
        _inputInfor.update { it.copy(dateOfBirth = dob) }
    }

    fun onGenderChange(gender: Boolean) {
        _inputInfor.update { it.copy(gender = gender) }
    }

    fun onOldPasswordChange(password: String) {
        _inputInfor.update { it.copy(oldPassword = password) }
    }

    fun onNewPasswordChange(password: String) {
        _inputInfor.update { it.copy(newPassword = password) }
    }

    fun popBackStack() {
        sendEvent(OneTimeEvent.PopBackStack)
    }

    private fun logout() {
        viewModelScope.launch {
            val status = logoutUseCase()
            if(status) {
                sendEvent(OneTimeEvent.Navigate(Route.LOGIN_SCREEN.toString()))
            } else {
                showSnackBar("Oops!")
            }
        }
    }

    private fun showSnackBar(message: String) {
        sendEvent(OneTimeEvent.ShowSnackbar(message))
    }

    private fun onNavToPrivateInformation(route: String) {
        sendEvent(OneTimeEvent.Navigate(route))
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send((event))
        }
    }

}