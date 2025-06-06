package com.tanh.tourbooking.presentation.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.domain.usecase.auth.CheckRoleUseCase
import com.tanh.tourbooking.domain.usecase.auth.ValidTokenUseCase
import com.tanh.tourbooking.util.Role
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val validTokenUseCase: ValidTokenUseCase,
    private val checkRoleUseCase: CheckRoleUseCase
): ViewModel() {

    private val _isTokenValid = MutableStateFlow<Boolean>(false)
    val isTokenValid: StateFlow<Boolean> = _isTokenValid

    private val _role = MutableStateFlow<Role>(Role.NULL)
    val role = _role.asStateFlow()

    fun getInfo() {
        viewModelScope.launch {
            launch {
                _isTokenValid.value = validTokenUseCase()
            }
            launch {
                _role.value = checkRoleUseCase()
            }
        }
    }

}