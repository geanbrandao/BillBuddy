package com.geanbrandao.br.billbuddy.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.ElevationOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo
import com.geanbrandao.br.billbuddy.ui.theme.TonalElevationTwo

@Composable
fun RoundedCornerCard(
    modifier: Modifier = Modifier,
    tonalElevation: Dp = TonalElevationTwo,
    isEnabled: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    RoundedCornerCardView(
        modifier = modifier,
        tonalElevation = tonalElevation,
        isEnabled = isEnabled,
        onClick = onClick,
        content = content,
    )
}

@Composable
private fun RoundedCornerCardView(
    modifier: Modifier = Modifier,
    tonalElevation: Dp = TonalElevationTwo,
    isEnabled: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Surface(
        modifier = modifier,
        enabled = isEnabled,
        onClick = onClick,
        shadowElevation = ElevationOne,
        tonalElevation = tonalElevation,
        shape = RoundedCornerShape(PaddingOne),
        content = content,   
    )
}

@Preview(showBackground = true)
@Composable
private fun RoundedCornerCardPreview() {
    BillBuddyTheme {
        RoundedCornerCardView(
            content = {
                Column(Modifier.padding(PaddingTwo)) {
                    Text(text = "Hello")
                    Text(text = "World")
                }
            }
        )
    }
}