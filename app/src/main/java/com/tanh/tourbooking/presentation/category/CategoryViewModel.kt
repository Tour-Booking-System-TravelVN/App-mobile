package com.tanh.tourbooking.presentation.category

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanh.tourbooking.data.model.util.exception.onError
import com.tanh.tourbooking.data.model.util.exception.onSuccess
import com.tanh.tourbooking.domain.usecase.tour.FoundTourUseCase
import com.tanh.tourbooking.presentation.util.OneTimeEvent
import com.tanh.tourbooking.util.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val foundTourUseCase: FoundTourUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryUiState())
    val state = _state.asStateFlow()

     var categoryName = mutableStateOf("")

    private val _channel = Channel<OneTimeEvent>()
    val channel = _channel.receiveAsFlow()

    init {
      viewModelScope.launch {
          categoryName.value = savedStateHandle.get<String>("categoryName") ?: ""
          foundTourUseCase(
              destination = "",
              price = "0-10000000",
              departureDate = null,
              page = 0
          ).apply {
              onSuccess { list ->
                  _state.update { state ->
                      state.copy(
                          list = list.filter { it.tour.category.categoryName == categoryName.value }
                      )
                  }
              }
              onError {

              }
          }
      }
    }

    fun onNavToDetailTour(id: String) {
        val foundTour = _state.value.list.first { it.tourUnitId == id }
        val jsonTour = Json.encodeToString(foundTour)
        val encodedJson = URLEncoder.encode(jsonTour, StandardCharsets.UTF_8.toString())
        val route = Route.DETAIL_SCREEN.toString() + "/${encodedJson}"
        sendEvent(OneTimeEvent.Navigate(route))
    }


    private fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            _channel.send((event))
        }
    }

}