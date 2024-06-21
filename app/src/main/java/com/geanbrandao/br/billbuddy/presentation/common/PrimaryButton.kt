package com.geanbrandao.br.billbuddy.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingFour

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PrimaryButtonView(
        text = text,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
private fun PrimaryButtonView(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(text)
    }
}

@Composable
fun FixedPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
) {
    FixedPrimaryButtonView(
        text = text,
        onClick = onClick,
        modifier = modifier,
        backgroundColor = backgroundColor,
    )
}

@Composable
private fun FixedPrimaryButtonView(
    text: String = "",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
) {
    Box(
        modifier
            .fillMaxWidth()
            .fadingEdge(getTopFade(backgroundColor))
            .background(color = backgroundColor)
            .padding(vertical = PaddingFour)
    ) {
        PrimaryButton(text = text, onClick = onClick)
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    BillBuddyTheme {
        PrimaryButtonView(text = "Button")
    }
}

//@Preview
//@Composable
//private fun FixedPrimaryButtonPreview() {
//    BillBuddyTheme {
//        BaseScreen()
//        Box(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.background)
//                .fillMaxSize()
//        ) {
//            LazyColumn {
//                items((1..100).toList()) {
//                    Text(text = "Content $it", Modifier.fillMaxSize())
//                }
//            }
//            FixedPrimaryButtonView(
//                text = "Button",
//                modifier = Modifier,
//            )
//        }
//    }
//}