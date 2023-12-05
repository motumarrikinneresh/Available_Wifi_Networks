package com.example.available_wifi_networks.wifiScanner

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.available_wifi_networks.R
import com.example.shimmerlibrary.Modifier
import com.google.gson.Gson
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WiFiNetworkDisplayScreen(navController: NavController) {



    var isLoading by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = true) {
        delay(5000)
        isLoading = false
    }
    val Modifier = Modifier(isLoading)
    val context = LocalContext.current
    val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

    val wifiList = remember {
        mutableStateOf<List<ScanResult>>(emptyList())
    }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                wifiManager.startScan()
                wifiList.value = wifiManager.scanResults
            }
        }
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        wifiManager.startScan()
        wifiList.value = wifiManager.scanResults
    } else {
        SideEffect {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Wi-Fi network detected",
                    color = Color.Green,
                    modifier = Modifier
                        .fillMaxSize(),
                        //.alpha(if (isLoading) 0f else 1f),
                    textAlign = TextAlign.Center
                )
            },
            backgroundColor = Color.White,
            modifier = Modifier
        )
    }) {
        Column(
            modifier = Modifier(isLoading = false).fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            LazyColumn(
                modifier = Modifier(false)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.White.copy(alpha = 0.9f))
                    .weight(0.9f)
            ) {

                item {
                    Spacer(
                        modifier = Modifier(false)
                            .background(Color.Green)
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                    Spacer(
                        modifier = Modifier(false)
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                    Text(
                        modifier = Modifier
                            .padding(10.dp),
                            //.ShimmerAnimate(isLoading)
                          //  .alpha(if (isLoading) 0f else 1f),
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Black)) {
                                append("Select the network to connect the Schneider charger to the ")
                            }
                            withStyle(style = SpanStyle(color = Color.Green)) {
                                append("Internet")
                            }

                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp


                    )
                    Spacer(
                        modifier = Modifier(false)
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                }
                if (wifiList.value.isNotEmpty()) {
                    val shortedList =
                        wifiList.value.sortedByDescending { it.level }
                            .filterNot { it.level < -90 }
                    println(shortedList)
                    items(shortedList.size) { index ->
                        val network: ScanResult = shortedList[index]
                        val ssid = network.SSID.toString()
                        val bssid = network.BSSID.toString()
                        val capabilities = network.capabilities.toString()
                        val channelWidth = network.channelWidth.toString()
                        val frequency = network.frequency.toString()
                        val level = network.level.toString()
                        val data =
                            DataForDetails(
                                ssid,
                                bssid,
                                capabilities,
                                channelWidth,
                                frequency,
                                level
                            )
                        val wifiDetails = Gson().toJson(data)

                        Card(
                            onClick = {
                                navController.navigate("DetailedInfo/$wifiDetails")
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                            elevation = 10.dp,
                            backgroundColor = Color.White

                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        //.ShimmerAnimate(isLoading)
                                        //.alpha(if (isLoading) 0f else 1f)
                                ) {
                                    val strength = network.level
                                    if (strength > -90 && strength < -80) {
                                        Image(
                                            painter = painterResource(id = R.drawable.baseline_wifi_1_bar_24),
                                            contentDescription = "LowWifi",
                                            modifier = Modifier
                                                //.ShimmerAnimate(isLoading)
                                                //      .alpha(if (isLoading) 0f else 1f)
                                                .padding(10.dp)
                                                .weight(0.20f),
                                            Alignment.Center,
                                        )
                                        Text(
                                            text = network.SSID.toString(),
                                            color = Color.Black,
                                            modifier = Modifier
                                                //.ShimmerAnimate(isLoading)
                                                //    .alpha(if (isLoading) 0f else 1f)
                                                .padding(top = 10.dp, bottom = 10.dp)
                                                .fillMaxWidth()
                                                .weight(0.60f),
                                            textAlign = TextAlign.Start,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        if (network.capabilities.contains("WPA")) {
                                            Image(
                                                painter = painterResource(id = R.drawable.baseline_lock_24),
                                                contentDescription = "Lock",
                                                modifier = Modifier
                                                    //.ShimmerAnimate(isLoading)
                                                    //      .alpha(if (isLoading) 0f else 1f)
                                                    .padding(10.dp)
                                                    .weight(0.20f),
                                                Alignment.Center
                                            )
                                        } else {
                                            Image(
                                                painter = painterResource(id = R.drawable.baseline_lock_open_24),
                                                contentDescription = "NoLock",
                                                modifier = Modifier
                                                    //.ShimmerAnimate(isLoading)
                                                    //    .alpha(if (isLoading) 0f else 1f)
                                                    .padding(10.dp)
                                                    .weight(0.20f),
                                                Alignment.Center
                                            )
                                        }
                                    } else if (strength > -80 && strength < -67) {
                                        Image(
                                            painter = painterResource(id = R.drawable.baseline_wifi_2_bar_24),
                                            contentDescription = "MediumWifi",
                                            modifier = Modifier
                                                //.ShimmerAnimate(isLoading)
                                                //.alpha(if (isLoading) 0f else 1f)
                                                .padding(10.dp)
                                                .weight(0.20f),
                                            Alignment.Center
                                        )
                                        Text(
                                            text = network.SSID.toString(),
                                            color = Color.Black,
                                            modifier = Modifier
                                                //.ShimmerAnimate(isLoading)
                                                //.alpha(if (isLoading) 0f else 1f)
                                                .padding(top = 10.dp, bottom = 10.dp)
                                                .fillMaxWidth()
                                                .weight(0.60f),
                                            textAlign = TextAlign.Start,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        if (network.capabilities.contains("WPA")) {
                                            Image(
                                                painter = painterResource(id = R.drawable.baseline_lock_24),
                                                contentDescription = "Lock",
                                                modifier = Modifier
                                                    // .ShimmerAnimate(isLoading)
                                                    //  .alpha(if (isLoading) 0f else 1f)
                                                    .padding(10.dp)
                                                    .weight(0.20f),
                                                Alignment.Center
                                            )
                                        } else {
                                            Image(
                                                painter = painterResource(id = R.drawable.baseline_lock_open_24),
                                                contentDescription = "NoLock",
                                                modifier = Modifier
                                                    //.ShimmerAnimate(isLoading)
                                                    //.alpha(if (isLoading) 0f else 1f)
                                                    .padding(10.dp)
                                                    .weight(0.20f),
                                                Alignment.Center
                                            )
                                        }
                                    } else if (strength > -67) {
                                        Image(
                                            painter = painterResource(id = R.drawable.baseline_wifi_24),
                                            contentDescription = "HighWifi",
                                            modifier = Modifier
                                                // .ShimmerAnimate(isLoading)
                                                //.alpha(if (isLoading) 0f else 1f)
                                                .padding(10.dp)
                                                .weight(0.20f),
                                            Alignment.Center
                                        )
                                        Text(
                                            text = network.SSID.toString(),
                                            color = Color.Black,
                                            modifier = Modifier
                                                //.ShimmerAnimate(isLoading)
                                                //.alpha(if (isLoading) 0f else 1f)
                                                .padding(top = 10.dp, bottom = 10.dp)
                                                .fillMaxWidth()
                                                .weight(0.60f),
                                            textAlign = TextAlign.Start,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        if (network.capabilities.contains("WPA")) {
                                            Image(
                                                painter = painterResource(id = R.drawable.baseline_lock_24),
                                                contentDescription = "Lock",
                                                modifier = Modifier
                                                    // .ShimmerAnimate(isLoading)
                                                    //  .alpha(if (isLoading) 0f else 1f)
                                                    .padding(10.dp)
                                                    .weight(0.20f),
                                                Alignment.Center
                                            )
                                        } else {
                                            Image(
                                                painter = painterResource(id = R.drawable.baseline_lock_open_24),
                                                contentDescription = "NoLock",
                                                modifier = Modifier
                                                    //.ShimmerAnimate(isLoading)
                                                    //.alpha(if (isLoading) 0f else 1f)
                                                    .padding(10.dp)
                                                    .weight(0.20f),
                                                Alignment.Center
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    item {
                        Text(
                            text = "No networks were available to connect the Schneider charger to the Internet",
                            color = Color.Black
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(0.1f)
                    .background(Color.White),
                verticalArrangement = Arrangement.Center
            )
            {
                Button(
                    onClick = { navController.navigate("WiFiNetworkDisplayScreen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 15.dp),
                        //.ShimmerAnimate(isLoading)
                        //.alpha(if (isLoading) 0f else 1f),
                    colors = ButtonDefaults.buttonColors(Color.Green)
                ) {
                    Text(
                        text = "Refresh",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAvailableWifiNetworksList() {
    WiFiNetworkDisplayScreen(rememberNavController())
}