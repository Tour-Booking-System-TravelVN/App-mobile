package com.tanh.tourbooking.util

import com.google.auth.oauth2.GoogleCredentials
import java.io.ByteArrayInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets

object AccessToken {

    private const val FIREBASE_MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging"

    fun getAccessToken(): String? {
        try {


            val stream = ByteArrayInputStream(jsonString.toByteArray(StandardCharsets.UTF_8))

            val googleCredentials = GoogleCredentials.fromStream(stream)
                .createScoped(listOf(FIREBASE_MESSAGING_SCOPE))

            googleCredentials.refresh()
            return googleCredentials.accessToken.tokenValue
        } catch (e: IOException) {
            return null
        }
    }

}