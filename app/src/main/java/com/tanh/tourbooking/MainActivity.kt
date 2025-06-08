package com.tanh.tourbooking

import android.Manifest
import android.app.ComponentCaller
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.datastore.dataStore
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tanh.tourbooking.data.serializer.AuthResultSerializer
import com.tanh.tourbooking.domain.repository.firestore.UserTokenRepository
import com.tanh.tourbooking.presentation.navigation.Navigation
import com.tanh.tourbooking.presentation.success.SuccessScreen
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import com.tanh.tourbooking.util.Route
import dagger.hilt.android.AndroidEntryPoint
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK
import javax.inject.Inject


val Context.dataStore by dataStore(
    fileName = "encrypted-token",
    serializer = AuthResultSerializer
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userTokenRepo: UserTokenRepository
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        askNotificationPermission()
        setContent {
            navController = rememberNavController()
            TourBookingTheme {
                Navigation(
                    navController = navController
                )
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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.let {
            setIntent(it)
            handleDeeplink(it)
        }
    }

    private fun handleDeeplink(intent: Intent) {
        intent.data?.let { uri ->
            Log.d("Zalo2", "Handling deeplink: $uri")
            when {
                uri.toString().contains("success") -> {
                    val orderCode = uri.getQueryParameter("orderCode")
                    Log.d("zalo2", "Payment Success, OrderCode: $orderCode")
                    navController.navigate(Route.SUCCESS_SCREEN.toString() + "/$orderCode") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
                uri.toString().contains("failure") -> {
                    Log.d("zalo2", "Payment Canceled or Failed")
                    navController.navigate(Route.FAILURE_SCREEN.toString()) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
                uri.toString().startsWith("zalopmt://app") -> {
                    Log.d("Zalo2", "ZaloPay deeplink triggered")
                    ZaloPaySDK.getInstance().onResult(intent)
                }
            }
        }
    }
}

