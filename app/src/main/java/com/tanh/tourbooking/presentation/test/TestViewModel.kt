package com.tanh.tourbooking.presentation.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.tanh.tourbooking.domain.repository.firestore.NotificationHandler
import com.tanh.tourbooking.domain.repository.firestore.UserTokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val userTokenRepository: UserTokenRepository,
    private val notificationHandler: NotificationHandler,
    private val firebaseMessaging: FirebaseMessaging
) : ViewModel() {

    val userId = 1
    val topic = "chat123"

    fun onEvent2() {
        notificationHandler.subscribeTopic(topic = topic)
    }

    fun onEvent() {
        viewModelScope.launch {
//            notificationHandler.sendNotificationByTopic(topic = topic)
        }
    }


}