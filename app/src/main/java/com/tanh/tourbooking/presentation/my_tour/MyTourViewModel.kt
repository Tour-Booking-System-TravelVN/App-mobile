package com.tanh.tourbooking.presentation.my_tour

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.model.MyTour
import com.tanh.tourbooking.domain.usecase.chatbox.ChatUseCaseManager
import com.tanh.tourbooking.domain.usecase.tour.GetMyTourUseCase
import com.tanh.tourbooking.domain.usecase.tour.RatingTourUseCase
import com.tanh.tourbooking.presentation.profile.ProfileUiState
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
class MyTourViewModel @Inject constructor(
    private val getMyTourUseCase: GetMyTourUseCase,
    private val ratingTourUseCase: RatingTourUseCase,
    private val chatUseCaseManager: ChatUseCaseManager
): ViewModel() {

    private val _state = MutableStateFlow(MyTourUiState())
    val state = _state.asStateFlow()

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            launch {
                getMyTourUseCase(
                    status = "done",
                    page = 0
                ).apply {
                    onSuccess { myTours ->
                        _state.update { state ->
                            state.copy(doneTours = myTours)
                        }
                    }
                }
            }
            launch {
                getMyTourUseCase(
                    status = "opw",
                    page = 0
                ).apply {
                    onSuccess { myTours ->
                        _state.update { state ->
                            state.copy(opwTours = myTours)
                        }
                    }
                }
            }
        }
    }

    fun ratingTour(comment: String, ratingValue: Int) {
        if(comment.trim().isBlank()) {
            showSnackBar("Vui lòng nhập nội dung")
            return
        }
        viewModelScope.launch {
            val tourUnitId = _state.value.currentTour?.tourUnit?.tourUnitId
            if (tourUnitId != null) {
                ratingTourUseCase(
                    tourUnitId = tourUnitId,
                    ratingValue = ratingValue,
                    comment = comment
                ).apply {
                    onSuccess {
                        showSnackBar("Đánh giá thành công")
                    }
                    onError {
                        showSnackBar("Bạn đã đánh giá rồi")
                    }
                }
            } else {
                showSnackBar("Không thể đánh giá")
            }
        }
    }

    fun navToChatBox() {
        viewModelScope.launch {
            val bookingId = _state.value.currentTour?.bookingId
            if(!bookingId.isNullOrBlank()) {
                chatUseCaseManager.getChatBoxIdByBookingId(bookingId).let { documentId ->
                    if(documentId != null) {
                        sendEvent(OneTimeEvent.Navigate(Route.MESSAGE_SCREEN.toString() + "/$documentId"))
                    } else {
                        showSnackBar("Nhóm chat không tồn tại")
                    }
                }
            } else {
                showSnackBar("Nhóm chat không tồn tại")
            }
        }
    }

    fun popBackStack() {
        sendEvent(OneTimeEvent.PopBackStack)
    }

    fun showSnackBar(message: String) {
        sendEvent(OneTimeEvent.ShowSnackbar(message))
    }

    fun onNavToDetailMyTour(myTour: MyTour) {
        _state.update { state ->
            state.copy(
                currentTour = myTour
            )
        }
        sendEvent(OneTimeEvent.Navigate(Route.DETAIL_MYTOUR_SCREEN.toString()))
    }

    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send((event))
        }
    }

}