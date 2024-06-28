package com.geanbrandao.br.billbuddy.presentation.billdetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.domain.model.ConsumedItemModel
import com.geanbrandao.br.billbuddy.domain.model.DividedValueModel
import com.geanbrandao.br.billbuddy.domain.model.SpentByPersonModel
import com.geanbrandao.br.billbuddy.presentation.billdetails.components.BillItem
import com.geanbrandao.br.billbuddy.presentation.billdetails.components.PersonItem
import com.geanbrandao.br.billbuddy.presentation.billdetails.components.TopAppBarBillDetails
import com.geanbrandao.br.billbuddy.presentation.billdetails.intents.BillDetailsIntent
import com.geanbrandao.br.billbuddy.presentation.billdetails.intents.BillDetailsIntent.OnConfirmationDialogRemoveItem
import com.geanbrandao.br.billbuddy.presentation.billdetails.intents.BillDetailsIntent.OnConfirmationDialogRemoveItemPositiveButtonClicked
import com.geanbrandao.br.billbuddy.presentation.billdetails.intents.BillDetailsNavigationIntent
import com.geanbrandao.br.billbuddy.presentation.billdetails.intents.BillDetailsNavigationIntent.NavigateBack
import com.geanbrandao.br.billbuddy.presentation.billdetails.intents.BillDetailsNavigationIntent.NavigateToCloseBill
import com.geanbrandao.br.billbuddy.presentation.billdetails.intents.BillDetailsNavigationIntent.NavigateToCreateItem
import com.geanbrandao.br.billbuddy.presentation.billdetails.state.BillDetailsUiState
import com.geanbrandao.br.billbuddy.presentation.common.BaseScreen
import com.geanbrandao.br.billbuddy.presentation.common.ConfirmationDialog
import com.geanbrandao.br.billbuddy.presentation.common.CustomTopAppBar
import com.geanbrandao.br.billbuddy.presentation.common.fadingEdge
import com.geanbrandao.br.billbuddy.presentation.common.getBottomFade
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingSix
import com.geanbrandao.br.billbuddy.ui.theme.PaddingThree
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.koin.androidx.compose.koinViewModel

@Composable
fun BillDetailsScreen(
    viewModel: BillDetailsViewModel = koinViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getBillDetails()
        }
    }
    val uiState = viewModel.uiState.collectAsState()
    BillDetailsView(
        onNavigationIntent = viewModel::handleNavigation,
        onBillDetailsIntent = viewModel::handleIntent,
        uiState = uiState.value,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BillDetailsView(
    modifier: Modifier = Modifier,
    uiState: BillDetailsUiState = BillDetailsUiState(),
    onNavigationIntent: (BillDetailsNavigationIntent) -> Unit = {},
    onBillDetailsIntent: (BillDetailsIntent) -> Unit = {},
) {
    val listState = rememberLazyListState()
    val isTopBarExpanded = remember { mutableStateOf(true) }

    BaseScreen(
        header = {
            Header(billName = uiState.billName, onNavigationIntent = onNavigationIntent)
        },
        content = {
            Box {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item {
                        BillBalance(uiState, onNavigationIntent)
                    }
                    item {
                        PersonsBalance(uiState)
                    }
                    items(
                        items = uiState.items,
                        key = {
                            it.itemId
                        }
                    ) { item: ConsumedItemModel ->
                        BillItem(
                            onRemoveClicked = {
                                onBillDetailsIntent(
                                    OnConfirmationDialogRemoveItem(
                                        isOpen = true,
                                        itemId = item.itemId
                                    )
                                )
                            },
                            item = item,
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.size(PaddingSix))
                    }
                }

                ConfirmationDialog(
                    isVisible = uiState.isConfirmationDialogOpen,
                    title = "Deseja excluir esse item?",
                    message = "Essa ação não pode ser desfeita",
                    onDismiss = { onBillDetailsIntent(OnConfirmationDialogRemoveItem(isOpen = false)) },
                    onConfirm = {
                        onBillDetailsIntent(
                            OnConfirmationDialogRemoveItemPositiveButtonClicked(
                                itemId = uiState.idItemToRemove
                            )
                        )
                        onBillDetailsIntent(OnConfirmationDialogRemoveItem(isOpen = false))
                    },
                )
                FloatingActionButton(
                    onClick = { onNavigationIntent(NavigateToCreateItem(billId = uiState.billId)) },
                    modifier = Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .padding(end = PaddingTwo, bottom = PaddingThree)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Adicionar uma item"
                    )
                }
            }
        }
    )
}

@Composable
private fun Header(
    billName: String,
    onNavigationIntent: (BillDetailsNavigationIntent) -> Unit,
) {

    CustomTopAppBar(
        title = billName,
        canNavigateBack = true,
        onArrowBackClicked = { onNavigationIntent(NavigateBack) },
        actionIcon = painterResource(id = R.drawable.ic_edit),
        actionIconContentDescription = "Editar conta",
        onActionClicked = { }
    )
}

@Composable
private fun PersonsBalance(
    uiState: BillDetailsUiState,
) {
    val lazyRowState = rememberLazyListState()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(PaddingTwo),
        contentPadding = PaddingValues(all = PaddingTwo),
        state = lazyRowState,
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(
            items = uiState.spentByPerson,
            key = { it.personId }
        ) { person: SpentByPersonModel ->
            PersonItem(person)
        }
    }

}

@Composable
private fun BillBalance(
    uiState: BillDetailsUiState,
    onNavigationIntent: (BillDetailsNavigationIntent) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(brush = getBottomFade(color = MaterialTheme.colorScheme.background))
            .fillMaxWidth()
            .padding(PaddingTwo)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Total: ",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = uiState.totalValueFormatted,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
            )

        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onNavigationIntent(NavigateToCloseBill(billId = uiState.billId)) },
        ) {
            Text(text = "Fechar")
        }
    }
}

@Preview
@Composable
private fun BillDetailsPreview() {

    BillBuddyTheme {
        BillDetailsView(uiState = getFakeUiState())
    }
}

private fun getFakeUiState() = BillDetailsUiState(
    billId = 1,
    billName = "Nome da conta",
    totalValue = 154.52f,
    items = listOf(
        ConsumedItemModel(
            itemId = 1,
            name = "Coca-cola",
            value = 10.0f,
            dividedValues = listOf(
                DividedValueModel(
                    userId = 1,
                    userName = "Pessoa 1",
                    value = 5.0f
                ),
                DividedValueModel(
                    userId = 2,
                    userName = "Pessoa 2",
                    value = 5.0f
                )
            )
        ),
        ConsumedItemModel(
            itemId = 2,
            name = "Pizza",
            value = 55f,
            dividedValues = listOf(
                DividedValueModel(
                    userId = 1,
                    userName = "Pessoa 1",
                    value = 18.34f
                ),
                DividedValueModel(
                    userId = 2,
                    userName = "Pessoa 2",
                    value = 18.33f
                ),
                DividedValueModel(
                    userId = 3,
                    userName = "Pessoa 3",
                    value = 18.33f
                )
            )
        ),
        ConsumedItemModel(
            itemId = 3,
            name = "Coca-cola",
            value = 10.0f,
            dividedValues = listOf(
                DividedValueModel(
                    userId = 1,
                    userName = "Pessoa 1",
                    value = 5.0f
                ),
                DividedValueModel(
                    userId = 2,
                    userName = "Pessoa 2",
                    value = 5.0f
                )
            )
        ),
        ConsumedItemModel(
            itemId = 4,
            name = "Pizza",
            value = 55f,
            dividedValues = listOf(
                DividedValueModel(
                    userId = 1,
                    userName = "Pessoa 1",
                    value = 18.34f
                ),
                DividedValueModel(
                    userId = 2,
                    userName = "Pessoa 2",
                    value = 18.33f
                ),
                DividedValueModel(
                    userId = 3,
                    userName = "Pessoa 3",
                    value = 18.33f
                )
            )
        ),
        ConsumedItemModel(
            itemId = 5,
            name = "Coca-cola",
            value = 10.0f,
            dividedValues = listOf(
                DividedValueModel(
                    userId = 1,
                    userName = "Pessoa 1",
                    value = 5.0f
                ),
                DividedValueModel(
                    userId = 2,
                    userName = "Pessoa 2",
                    value = 5.0f
                )
            )
        ),
        ConsumedItemModel(
            itemId = 6,
            name = "Pizza",
            value = 55f,
            dividedValues = listOf(
                DividedValueModel(
                    userId = 1,
                    userName = "Pessoa 1",
                    value = 18.34f
                ),
                DividedValueModel(
                    userId = 2,
                    userName = "Pessoa 2",
                    value = 18.33f
                ),
                DividedValueModel(
                    userId = 3,
                    userName = "Pessoa 3",
                    value = 18.33f
                )
            )
        ),
        ConsumedItemModel(
            itemId = 7,
            name = "Coca-cola",
            value = 10.0f,
            dividedValues = listOf(
                DividedValueModel(
                    userId = 1,
                    userName = "Pessoa 1",
                    value = 5.0f
                ),
                DividedValueModel(
                    userId = 2,
                    userName = "Pessoa 2",
                    value = 5.0f
                )
            )
        ),
        ConsumedItemModel(
            itemId = 8,
            name = "Pizza",
            value = 55f,
            dividedValues = listOf(
                DividedValueModel(
                    userId = 1,
                    userName = "Pessoa 1",
                    value = 18.34f
                ),
                DividedValueModel(
                    userId = 2,
                    userName = "Pessoa 2",
                    value = 18.33f
                ),
                DividedValueModel(
                    userId = 3,
                    userName = "Pessoa 3",
                    value = 18.33f
                )
            )
        ),
    ),
    spentByPerson = listOf(
        SpentByPersonModel(
            billId = 1,
            personId = 1,
            name = "Pessoa 1",
            totalSpent = 45.0f
        ),
        SpentByPersonModel(
            billId = 1,
            personId = 2,
            name = "Pessoa 2",
            totalSpent = 55.0f
        ),
        SpentByPersonModel(
            billId = 1,
            personId = 3,
            name = "Pessoa 3",
            totalSpent = 62.3f
        ),
        SpentByPersonModel(
            billId = 1,
            personId = 4,
            name = "Pessoa 4",
            totalSpent = 31.4f
        ),
    )
)