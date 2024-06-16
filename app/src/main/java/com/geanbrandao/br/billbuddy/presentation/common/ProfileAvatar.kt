package com.geanbrandao.br.billbuddy.presentation.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.StrokeSizeAvatar

@Composable
fun ProfileAvatar(
    initials: String,
    sizeDp: Dp,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    letterColor: Color = MaterialTheme.colorScheme.onBackground,
    strokeColor: Color = MaterialTheme.colorScheme.primary,
) {
    ProfileAvatarView(
        modifier = modifier,
        initials = initials,
        sizeDp = sizeDp,
        backgroundColor = backgroundColor,
        letterColor = letterColor,
        strokeColor = strokeColor,
    )
}

@Composable
private fun ProfileAvatarView(
    initials: String,
    sizeDp: Dp,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    letterColor: Color = MaterialTheme.colorScheme.onBackground,
    strokeColor: Color = MaterialTheme.colorScheme.primary,
) {
    val textMeasurer = rememberTextMeasurer()
    val canvasSize = 300.dp
    val scale: Float = sizeDp.value / canvasSize.value

    Canvas(modifier = modifier.size(sizeDp)) {
        val radius = (canvasSize.toPx() / 2)
        scale(scale = scale) {
            drawCircle(
                color = backgroundColor,
                radius = radius
            )
            drawCircle(
                color = strokeColor,
                radius = radius,
                style = Stroke(width = StrokeSizeAvatar.toPx())
            )

            val textStyle = TextStyle(
                color = letterColor,
                fontSize = (canvasSize / 2).toSp(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            val textLayoutResult = textMeasurer.measure(
                text = initials,
                style = textStyle
            )

            val textX = (size.width - textLayoutResult.size.width) / 2
            val textY = (size.height - textLayoutResult.size.height) / 2

            drawText(
                textLayoutResult,
                topLeft = Offset(textX, textY)
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_7")
@Composable
private fun ProfileAvatarPreview() {
    BillBuddyTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ProfileAvatarView(
                modifier = Modifier,
                backgroundColor = Color.Blue,
                letterColor = Color.White,
                strokeColor = Color.Black,
                initials = "AB",
                sizeDp = 48.dp,
            )
        }
    }
}