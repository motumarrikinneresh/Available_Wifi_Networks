package com.example.available_wifi_networks.wifiScanner

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color

@Composable
fun Modifier.Shimmer(isLoading: Boolean): Modifier = composed {
    if (isLoading) {
        background(Color.White)
    } else {
        return@composed Modifier
    }
}