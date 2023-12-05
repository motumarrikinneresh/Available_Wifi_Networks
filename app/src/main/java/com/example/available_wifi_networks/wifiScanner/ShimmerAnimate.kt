package com.example.available_wifi_networks.wifiScanner

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@Composable

fun ShimmerAnimate(navController: NavController) {
    val colors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition()

    val translateAnimate = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                delayMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = Offset(x = translateAnimate.value, y = translateAnimate.value),

        )
    var isLoading by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = true) {
        delay(3000)
        isLoading = false
    }

    if (isLoading) {
        Column(modifier = Modifier.background(Color.White)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.10f)
                    //.background(ShimmerAnimate())
            )

            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .weight(0.025f)
                   // .background(ShimmerAnimate()),
            )

            Box(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth(0.5f)
                    .weight(0.025f)
                    //.background(ShimmerAnimate())

            )

            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .weight(0.70f)
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                repeat(10) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                           // .background(ShimmerAnimate())
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                               // .background(ShimmerAnimate(Color.Green))
                        )
                        {
                            Row(modifier = Modifier.fillMaxSize()) {
                                Box(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .fillMaxHeight()
                                        //.background(ShimmerAnimate())
                                )

                                Spacer(modifier = Modifier.weight(0.025f))
                                Box(
                                    modifier = Modifier
                                        .weight(0.7f)
                                        .fillMaxHeight()
                                       // .background(ShimmerAnimate())
                                )
                                Spacer(modifier = Modifier.weight(0.025f))
                                Box(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .fillMaxHeight()
                                       // .background(ShimmerAnimate())
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)
                            //.background(ShimmerAnimate())
                    )
                }
            }
        }
    } else {
        WiFiNetworkDisplayScreen(navController = navController)
    }
}

@Composable
@Preview()
fun ShimmerItemPreview() {
    ShimmerAnimate(rememberNavController())
}
