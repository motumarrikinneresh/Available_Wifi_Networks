package com.example.available_wifi_networks.wifiScanner

data class DataForDetails(
    val ssid:String,
    val bssid:String,
    val capabilities:String,
    val channelWidth:String,
    val frequency:String,
    val level:String
)
