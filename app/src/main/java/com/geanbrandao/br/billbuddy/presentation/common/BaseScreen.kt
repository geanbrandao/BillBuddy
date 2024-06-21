package com.geanbrandao.br.billbuddy.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.FooterHeight
import com.geanbrandao.br.billbuddy.ui.theme.PaddingFour
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    header: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
    fixedFooter: (@Composable BoxScope.() -> Unit)? = null,
) {
    BaseScreenView(
        modifier = modifier,
        header = header,
        content = content,
        fixedFooter = fixedFooter,
    )
}

@Composable
private fun BaseScreenView(
    modifier: Modifier = Modifier,
    header: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
    fixedFooter: (@Composable BoxScope.() -> Unit)? = null,
) {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                header()
                content()
            }
            if (fixedFooter != null) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.BottomCenter)
                        .height(height = FooterHeight)
                        .background(brush = getTopFade(MaterialTheme.colorScheme.background)),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    fixedFooter()
                }
            }
        }
    }
}

@Preview
@Composable
private fun BaseScreenPreview() {
    BillBuddyTheme {
        BaseScreenView(
            header = {
                CustomTopAppBar(
                    title = "Title",
                    canNavigateBack = true,
                    onArrowBackClicked = { /*TODO*/ }
                )
            },
            content = {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items((1..100).toList()) {
                        Text(text = "Content $it")
                    }
                }
            },
//            fixedFooter = null,
            fixedFooter = {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PaddingTwo)
                        .padding(bottom = PaddingFour)
                ) {
                    Text(text = "Salvar")
                }
            },
        )
    }
}