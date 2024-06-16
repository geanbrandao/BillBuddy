package com.geanbrandao.br.billbuddy.presentation.bills

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.domain.model.BillModel
import com.geanbrandao.br.billbuddy.presentation.bills.components.BillItem
import com.geanbrandao.br.billbuddy.presentation.bills.intents.BillsIntent
import com.geanbrandao.br.billbuddy.presentation.bills.intents.BillsIntent.OnConfirmationDialogRemoveBill
import com.geanbrandao.br.billbuddy.presentation.bills.intents.BillsIntent.OnConfirmationDialogRemoveBillPositiveButtonClicked
import com.geanbrandao.br.billbuddy.presentation.bills.intents.BillsNavigationIntent
import com.geanbrandao.br.billbuddy.presentation.bills.intents.BillsNavigationIntent.NavigateToBill
import com.geanbrandao.br.billbuddy.presentation.bills.intents.BillsNavigationIntent.NavigateToBillDetails
import com.geanbrandao.br.billbuddy.presentation.bills.intents.BillsNavigationIntent.NavigateToGroups
import com.geanbrandao.br.billbuddy.presentation.bills.state.BillsUiState
import com.geanbrandao.br.billbuddy.presentation.common.BaseScreen
import com.geanbrandao.br.billbuddy.presentation.common.ConfirmationDialog
import com.geanbrandao.br.billbuddy.presentation.common.CustomTopAppBar
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo
import org.koin.androidx.compose.koinViewModel

@Composable
fun BillsScreen(
    viewModel: BillsViewModel = koinViewModel()
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getBills()
        }
    }
    val uiState = viewModel.uiState.collectAsState()
    BillsView(
        onNavigationIntent = viewModel::handleNavigation,
        onIntent = viewModel::handleIntent,
        uiState = uiState.value,
    )
}

@Composable
private fun BillsView(
    modifier: Modifier = Modifier,
    uiState: BillsUiState = BillsUiState(),
    onIntent: (BillsIntent) -> Unit = {},
    onNavigationIntent: (BillsNavigationIntent) -> Unit = {},
) {
    BaseScreen(
        header = { Header(onNavigationIntent) },
        content = {
            Column(
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(horizontal = PaddingTwo)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    item {
                        Spacer(modifier = Modifier.size(size = PaddingTwo))
                        Text(
                            text = "Contas cadastradas",
                            style = MaterialTheme.typography.labelLarge,
                        )
                        Spacer(modifier = Modifier.size(size = PaddingTwo))
                    }
                    items(uiState.bills) { billItem: BillModel ->
                        BillItem(
                            modifier = Modifier.padding(vertical = PaddingOne),
                            billItem = billItem,
                            onRemoveClicked = {
                                onIntent(OnConfirmationDialogRemoveBill(isOpen = true, billId = billItem.id))
                            },
                            onItemClicked = {
                                onNavigationIntent(NavigateToBillDetails(id = billItem.id))
                            }
                        )
                    }
                }
                Button(
                    onClick = {
                        onNavigationIntent(NavigateToBill(-1))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = PaddingTwo)
                ) {
                    Text(text = "Criar conta")
                }
                ConfirmationDialog(
                    isVisible = uiState.isConfirmationDialogOpen,
                    title = "Deseja excluir essa conta?",
                    message = "Todas os dados e pessoas dessa conta serão removidos. Essa ação não poderá ser desfeita.",
                    onDismiss = {
                        onIntent(OnConfirmationDialogRemoveBill(isOpen = false))
                    },
                    onConfirm = {
                        onIntent(OnConfirmationDialogRemoveBillPositiveButtonClicked(billId = uiState.idBillToRemove))
                        onIntent(OnConfirmationDialogRemoveBill(isOpen = false))
                    },
                )
            }
        }
    )
}

@Composable
private fun Header(
    onNavigationIntent: (BillsNavigationIntent) -> Unit,
) {
    CustomTopAppBar(
        title = stringResource(id = R.string.screen_bills_top_bar_title),
        canNavigateBack = false,
        onArrowBackClicked = {  },
        actionIcon = painterResource(id = R.drawable.ic_groups),
        actionIconContentDescription = "Acessar grupos",
        onActionClicked = { onNavigationIntent(NavigateToGroups) },
    )
}

@Preview
@Composable
private fun BillsPreview() {
    BillBuddyTheme {
        val uiState = BillsUiState(
            bills = listOf(
                BillModel(id = 1, name = "Conta 1", status = "Em aberto", total = "R$ 10,00"),
                BillModel(id = 2, name = "Conta 2", status = "Nova", total = "R$ 10,00"),
                BillModel(id = 3, name = "Conta 3", status = "Fechada", total = "R$ 10,00"),
            )
        )
        BillsView(uiState = uiState)
    }
}