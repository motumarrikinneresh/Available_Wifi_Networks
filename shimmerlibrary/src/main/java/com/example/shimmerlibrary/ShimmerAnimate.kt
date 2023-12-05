package com.example.shimmerlibrary

import androidx.compose.ui.graphics.Color
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun Modifier(
    isLoading: Boolean = false,
    color: Color = Color.LightGray,
    key: String = "brush"
): Modifier {
    val colors = listOf(
        color.copy(alpha = 0.8f),
        color.copy(alpha = 0.4f),
        color.copy(alpha = 0.8f)
    )
    val brushes: HashMap<String, Brush> = HashMap()

    val transition = rememberInfiniteTransition()

    val translateAnimate = transition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
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

    brushes["brush"] = brush
    val color1 = brushes[key]!!
//    if (isLoading) {
//        background(color)
//    } else {
//        return@composed Modifier
//    }
    return Modifier.run {
        if (isLoading) {
            background(color1)
                .alpha(0f)
        } else return Modifier
    }
}