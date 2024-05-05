package com.geanbrandao.br.billbuddy.presentation.bill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.presentation.bill.BillNavigationIntent.NavigateToBillDetails
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingFour
import com.geanbrandao.br.billbuddy.ui.theme.PaddingThree
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo
import org.koin.androidx.compose.koinViewModel

@Composable
fun BillScreen(
    viewModel: BillViewModel = koinViewModel()
) {
    BillView(
        onNavigationIntent = viewModel::handleNavigation,
    )
}

@Composable
private fun BillView(
    modifier: Modifier = Modifier,
    onNavigationIntent: (BillNavigationIntent) -> Unit = {}
) {
    Surface {
        Column(
            modifier = modifier
                .padding(horizontal = PaddingTwo)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.size(PaddingThree))
            BillNameInput(
                text = "",
                onTextChange = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(PaddingTwo))
            PersonNameInput(
                text = "",
                onTextChange = {},
                onAddPerson = {},
                modifier = Modifier.fillMaxWidth()
            )
            PersonsList(
                list = listOf("Pessoa 1", "Pessoa 2"),
                onRemoveClicked = { },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    onNavigationIntent(NavigateToBillDetails(-1))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = PaddingFour, top = PaddingThree)
            ) {
                Text(text = "Salvar")
            }
        }
    }
}

@Preview
@Composable
private fun BillPreview() {
    BillBuddyTheme {
        BillView()
    }
}