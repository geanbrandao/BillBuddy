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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.presentation.bill.BillIntent.OnAddNewPerson
import com.geanbrandao.br.billbuddy.presentation.bill.BillIntent.OnBillNameChange
import com.geanbrandao.br.billbuddy.presentation.bill.BillIntent.OnPersonNameChange
import com.geanbrandao.br.billbuddy.presentation.bill.BillIntent.OnRemovePerson
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

    val uiState = viewModel.uiState.collectAsState()

    BillView(
        uiState = uiState.value,
        onBillIntent = viewModel::handleIntents,
        onNavigationIntent = viewModel::handleNavigation,
    )
}

@Composable
private fun BillView(
    modifier: Modifier = Modifier,
    uiState: BillUiState = BillUiState(),
    onBillIntent: (BillIntent) -> Unit = {},
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
                text = uiState.billName,
                onTextChange = { text: String ->
                    onBillIntent(OnBillNameChange(value = text))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(PaddingTwo))
            PersonNameInput(
                text = uiState.personName,
                onTextChange = { text: String ->
                    onBillIntent(OnPersonNameChange(value = text))
                },
                onAddPerson = {
                    onBillIntent(OnAddNewPerson(value = uiState.personName))
                },
                modifier = Modifier.fillMaxWidth()
            )
            PersonsList(
                list = uiState.persons,
                onRemoveClicked = { personName: String ->
                    onBillIntent(OnRemovePerson(value = personName))
                },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    onNavigationIntent(NavigateToBillDetails(-1))
                },
                enabled = uiState.isEnabled,
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