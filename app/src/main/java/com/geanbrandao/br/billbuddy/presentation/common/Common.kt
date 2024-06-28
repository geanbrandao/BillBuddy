package com.geanbrandao.br.billbuddy.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun getTopFade(color: Color) = Brush.verticalGradient(
    0f to Color.Transparent,
    0.3f to color,
)

@Composable
fun getBottomFade(color: Color) = Brush.verticalGradient(
    0.7f to color,
    1f to Color.Transparent
)

//val topBottomFade = Brush.verticalGradient(0f to Color.Transparent, 0.3f to Color.Red, 0.7f to Color.Red, 1f to Color.Transparent)

fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }