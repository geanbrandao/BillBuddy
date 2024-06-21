package com.geanbrandao.br.billbuddy.presentation.bill

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.presentation.bill.components.BillNameInput
import com.geanbrandao.br.billbuddy.presentation.bill.components.PersonNameInput
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillIntent
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillIntent.OnAddNewPerson
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillIntent.OnBillNameChange
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillIntent.OnCreateBill
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillIntent.OnPersonNameChange
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillIntent.OnRemovePerson
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillNavigationIntent
import com.geanbrandao.br.billbuddy.presentation.bill.state.BillUiState
import com.geanbrandao.br.billbuddy.presentation.common.BaseScreen
import com.geanbrandao.br.billbuddy.presentation.common.CustomTopAppBar
import com.geanbrandao.br.billbuddy.presentation.common.PersonItem
import com.geanbrandao.br.billbuddy.presentation.common.PrimaryButton
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
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
    BaseScreen(
        header = { Header(onNavigationIntent) },
        content = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Spacer(modifier = Modifier.size(PaddingThree))
                BillNameInput(
                    text = uiState.billName,
                    onTextChange = { text: String ->
                        onBillIntent(OnBillNameChange(value = text))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PaddingTwo),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PaddingTwo),
                )
                PersonsView(
                    persons = uiState.persons,
                    onRemoveClicked = { personName: String ->
                        onBillIntent(OnRemovePerson(value = personName))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        fixedFooter = {
            PrimaryButton(
                text = "Salvar",
                onClick = { onBillIntent(OnCreateBill) },
            )
        }
    )
}

@Composable
private fun PersonsView(
    modifier: Modifier = Modifier,
    persons: List<String> = listOf(),
    onRemoveClicked: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = PaddingSeven)
    ) {
        item {
            Text(
                text = "Quem está dividindo a conta?",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(vertical = PaddingTwo)
                    .padding(horizontal = PaddingTwo)
                    .fillMaxWidth()
            )
        }

        items(
            items = persons,
            key = { it.hashCode() }
        ) {
            PersonItem(
                name = it,
                groupName = "", // TODO vai existir quando foi adicionado a partir de um grupo
                initials = "A", // TODO gerar na viewModel com useCase
                onRemovePerson = { onRemoveClicked(it) },
            )
        }
    }
}

@Composable
private fun Header(
    onNavigationIntent: (BillNavigationIntent) -> Unit,
) {
    CustomTopAppBar(
        title = stringResource(id = R.string.screen_bills_top_bar_title),
        canNavigateBack = true,
        onArrowBackClicked = {
//            onNavigationIntent(BillNavigationIntent.NavigateBack)
        },
        actionIcon = painterResource(id = R.drawable.ic_groups),
    )
}

@Composable
private fun getBackgroundButtonGradient() = Brush.verticalGradient(
    colors = listOf(
        MaterialTheme.colorScheme.background.copy(0.8f),
        MaterialTheme.colorScheme.background,
    ),
)

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true, device = "id:pixel_4a")
@Composable
private fun BillPreview() {
    BillBuddyTheme {
        val uiState = BillUiState(
            billName = "Lancheria do José",
            personName = "",
            persons = listOf(
                "Maria Clara",
                "João Pessoa",
                "José da Silva",
                "Gustavo Farias",
                "Mario Santos",
                "Bia Joana",
                "Carla Silva",
                "Fulana de Tal",
                "Antonieta Garilbadi",
            ),
        )
        BillView(uiState = uiState)
    }
}