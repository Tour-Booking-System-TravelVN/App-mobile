package com.tanh.tourbooking

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.datastore.dataStore
import com.tanh.tourbooking.data.serializer.AuthResultSerializer
import com.tanh.tourbooking.domain.repository.firestore.UserTokenRepository
import com.tanh.tourbooking.presentation.navigation.Navigation
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

val Context.dataStore by dataStore(
    fileName = "encrypted-token",
    serializer = AuthResultSerializer
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userTokenRepo: UserTokenRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        askNotificationPermission()
        setContent {
            TourBookingTheme {
                Navigation()
            }
        }
    }

    private fun askNotificationPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {

            } else if(shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {

            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {

        }
    }
}

