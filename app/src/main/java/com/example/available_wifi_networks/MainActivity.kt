package com.example.available_wifi_networks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.available_wifi_networks.ui.theme.Available_Wifi_NetworksTheme
import com.example.available_wifi_networks.wifiScanner.DataForDetails
import com.example.available_wifi_networks.wifiScanner.DetailedInfo
import com.example.available_wifi_networks.wifiScanner.ShimmerAnimate
import com.example.available_wifi_networks.wifiScanner.WiFiNetworkDisplayScreen
import com.google.gson.Gson
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Available_Wifi_NetworksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var isLoading by remember {
                        mutableStateOf(true)
                    }
                    LaunchedEffect(key1 = true) {
                        delay(2000)
                        isLoading = false
                    }
                    val navController = rememberNavController()
                    WiFiNetworkDisplayScreen(navController = navController)
                    NavHost(
                        navController = navController,
                        startDestination = "WiFiNetworkDisplayScreen"
                    ) {
                        composable("ShimmerAnimate"){
                            ShimmerAnimate(navController)
                        }
                        composable(
                            "WiFiNetworkDisplayScreen"
                        ) {
                            WiFiNetworkDisplayScreen(navController = navController)
                        }
                        composable("DetailedInfo/{wifiDetails}",
                        arguments = listOf(
                            navArgument("wifiDetails"){
                                type= NavType.StringType
                            }
                        )
                        ){
                            it.arguments?.getString("wifiDetails")?.let {json->
                                val wifiDetails= Gson().fromJson(json,DataForDetails::class.java)
                                DetailedInfo(wifiDetails, navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}