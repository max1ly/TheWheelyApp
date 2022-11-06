package com.max.wheely.screens

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun WheelScreen(
    viewModel: WheelViewModel,
    navController: NavController,
) {
    viewModel.optionsOnWheel()
    val optionsOnWheel = viewModel.optionsOnWheel.collectAsState()
    val config = LocalConfiguration.current
    val currentRotation = remember { mutableStateOf(0f) }
    val rotation = remember { Animatable(currentRotation.value) }
    val wheelState = viewModel.wheelState.collectAsState().value
    val isPlaying = when (wheelState) {
        WheelState.Idle -> false
        WheelState.Spinning -> true
        else -> false
    }

    Column(modifier = Modifier.fillMaxHeight()) {
        LaunchedEffect(isPlaying) {
            if (isPlaying) {
                rotation.animateTo(
                    targetValue = currentRotation.value + viewModel.randomAngle(),
                    animationSpec = TweenSpec(
                        durationMillis = 3000,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotation.value = value
                }
                viewModel.onWheelSelectedAngle(currentRotation.value)
            }
        }
        Box {
            Wheel(
                options = optionsOnWheel.value,
                size = config.screenWidthDp.dp,
                rotation = rotation.value,
            )
            Text(
                modifier = Modifier.padding(
                    top = (config.screenWidthDp.dp - 34.dp) / 2, // TODO: fix hardcoded values
                    start = config.screenWidthDp.dp - 30.dp
                ),
                text = "\u25C0",
                fontSize = 24.sp,
            )
        }
        if (wheelState is WheelState.Selected) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = wheelState.option.message,
                fontSize = 24.sp,
            )
        }
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .weight(1f)
        )
        Button(
            onClick = { viewModel.onWheelSpin() },
            enabled = isPlaying.not(),
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .height(56.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Spin")
        }
    }
}

@Composable
fun Wheel(
    options: List<OptionOnWheel>,
    size: Dp,
    canvasPadding: Dp = 16.dp,
    rotation: Float = 0f,
) {
    Canvas(
        modifier = Modifier
            .rotate(rotation)
            .size(size)
            .padding(all = canvasPadding),
    ) {
        val wheelRadius = this.size.width / 2
        drawCircle(
            color = Color.LightGray,
            style = Fill,
            radius = wheelRadius,
            center = center
        )
        drawCircle(
            color = Color.Gray,
            style = Stroke(5f),
            radius = wheelRadius
        )
        drawIntoCanvas {
            it.nativeCanvas.apply {
                options.forEachIndexed { index, optionOnWheel ->
                    if (index != 0 && options.size > 1) {
                        rotate(-optionOnWheel.angle / 2, wheelRadius, wheelRadius)
                    }
                    drawText(
                        optionOnWheel.option.message,
                        center.x + 250, //TODO: get rid of the hardcoded values
                        center.y + 18,
                        textPaint
                    )
                    if (options.size > 1) {
                        rotate(-optionOnWheel.angle / 2, wheelRadius, wheelRadius)
                        drawLine(
                            color = Color.Gray,
                            start = center,
                            end = center.copy(
                                x = center.x + wheelRadius,
                                y = center.y
                            ),
                        )
                    }
                }
            }
        }
    }

}

val textPaint = Paint().apply {
    flags = Paint.ANTI_ALIAS_FLAG
    color = Color.Black.toArgb()
    textSize = 50f
}

