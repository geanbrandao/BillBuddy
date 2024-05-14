package com.geanbrandao.br.billbuddy.presentation.bill

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.presentation.bill.components.BillNameInput
import com.geanbrandao.br.billbuddy.presentation.bill.components.PersonNameInput
import com.geanbrandao.br.billbuddy.presentation.bill.components.PersonsList
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillIntent
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillIntent.OnAddNewPerson
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillIntent.OnBillNameChange
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillIntent.OnCreateBill
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillIntent.OnPersonNameChange
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillIntent.OnRemovePerson
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillNavigationIntent
import com.geanbrandao.br.billbuddy.presentation.bill.state.BillUiState
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingFour
import com.geanbrandao.br.billbuddy.ui.theme.PaddingSeven
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
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = PaddingTwo)
                .padding(bottom = PaddingSeven)
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
                    keyboardController?.hide()
                    onBillIntent(OnAddNewPerson(value = uiState.personName))
                },
                modifier = Modifier.fillMaxWidth()
            )
            PersonsList(
                list = uiState.persons,
                onRemoveClicked = { personName: String ->
                    onBillIntent(OnRemovePerson(value = personName))
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(
            onClick = {
                onBillIntent(OnCreateBill)
            },
            enabled = uiState.isEnabled,
            modifier = Modifier
                .background(brush = getBackgroundButtonGradient())
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = PaddingTwo)
                .padding(bottom = PaddingFour)
                .padding(top = PaddingFour)
        ) {
            Text(text = "Salvar")
        }
    }
}

@Composable
private fun getBackgroundButtonGradient() = Brush.verticalGradient(
    colors = listOf(
        Color.Transparent,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.background,
    ),
)

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun BillPreview() {
    BillBuddyTheme {
        BillView()
    }
}