package com.geanbrandao.br.billbuddy.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    BaseScreenView(
        modifier = modifier,
        header = header,
        content = content
    )
}

@Composable
fun BaseScreenView(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Surface {
        Column {
            header()
            content()
        }
    }
}

@Preview
@Composable
private fun BaseScreenPreview() {
    BillBuddyTheme {
        BaseScreen(
            header = {
                CustomTopAppBar(
                    title = "Title",
                    canNavigateBack = true,
                    onArrowBackClicked = { /*TODO*/ }
                )
            },
            content = {
                Text(text = "Content")
            }
        )
    }
}