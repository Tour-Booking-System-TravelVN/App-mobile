package com.tanh.tourbooking.presentation.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.domain.usecase.auth.ValidTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val validTokenUseCase: ValidTokenUseCase
): ViewModel() {

    private val _isTokenValid = MutableStateFlow<Boolean>(false)
    val isTokenValid: StateFlow<Boolean> = _isTokenValid

    init {
        viewModelScope.launch {
            _isTokenValid.value = validTokenUseCase()
            Log.d("TOK1", _isTokenValid.value.toString())

        }
    }

}