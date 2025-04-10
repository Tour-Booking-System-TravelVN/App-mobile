package com.tanh.tourbooking.data.repository.firestore

import android.util.Log
import androidx.compose.animation.core.snap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.tanh.tourbooking.di.IODispatcher
import com.tanh.tourbooking.domain.repository.firestore.UserTokenRepository
import com.tanh.tourbooking.util.Collections
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserTokenRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseMessaging: FirebaseMessaging,
    @IODispatcher private val dispatcherIO: CoroutineDispatcher
) : UserTokenRepository {

    private val userRef = firestore.collection(Collections.USER)

    override suspend fun getUserTokenById(userId: Int): String? {
        return withContext(dispatcherIO) {
            val snapshot = userRef.whereEqualTo("userId", userId).get().await()
            if(!snapshot.isEmpty) {
                snapshot.first().getString("userToken")
            } else {
                null
            }
        }
    }

    override suspend fun updateUserToken(userId: Int, token: String) {
        withContext(dispatcherIO) {
            val map = hashMapOf(
                "userId" to userId,
                "userToken" to token
            )
            userRef.add(map).addOnSuccessListener {
                Log.d("FCM1", "Success")
            }.addOnFailureListener  {
                Log.d("FCM1", "Fail")
            }
        }
    }

    override suspend fun fetchAndSaveFCMToken(userId: Int) {
        withContext(dispatcherIO) {
            firebaseMessaging.token
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("FCM", "Fetching FCM token failed", task.exception)
                        return@addOnCompleteListener
                    }
                    val token = task.result
                    Log.d("FCM", "FCM Token: $token")
                    CoroutineScope(dispatcherIO).launch {
                        Log.d("FCM", "Running coroutine")
                        updateUserToken(userId, token)
                    }
                }
        }
    }

}