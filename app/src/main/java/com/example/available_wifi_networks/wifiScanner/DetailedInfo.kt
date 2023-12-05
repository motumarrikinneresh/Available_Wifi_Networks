package com.example.available_wifi_networks.wifiScanner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable

fun DetailedInfo(wifiDetails: DataForDetails, navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Wi-Fi network details",
                    color = Color.Green,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 13.dp)
                )
            },
            backgroundColor = Color.White,
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color.Green)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .padding(),
                elevation = 50.dp,
                backgroundColor = Color.White,
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "SSID : " + wifiDetails.ssid,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "BSSID : " + wifiDetails.bssid,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "CAPABILITIES : " + wifiDetails.capabilities,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "FREQUENCY : " + wifiDetails.frequency,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "CHANNEL WIDTH : " + wifiDetails.channelWidth,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "LEVEL : " + wifiDetails.level,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}