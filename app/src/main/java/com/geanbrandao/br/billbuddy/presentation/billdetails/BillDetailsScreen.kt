package com.geanbrandao.br.billbuddy.presentation.billdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.geanbrandao.br.billbuddy.domain.model.ConsumedItemModel
import com.geanbrandao.br.billbuddy.domain.model.DividedValueModel
import com.geanbrandao.br.billbuddy.presentation.billdetails.BillDetailsNavigationIntent.NavigateBack
import com.geanbrandao.br.billbuddy.presentation.billdetails.BillDetailsNavigationIntent.NavigateToCloseBill
import com.geanbrandao.br.billbuddy.presentation.billdetails.BillDetailsNavigationIntent.NavigateToCreateItem
import com.geanbrandao.br.billbuddy.presentation.common.ConfirmationDialog
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingFive
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
        uiState = uiState.value,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BillDetailsView(
    modifier: Modifier = Modifier,
    uiState: BillDetailsUiState = BillDetailsUiState(),
    onNavigationIntent: (BillDetailsNavigationIntent) -> Unit = {},
) {
    val listState = rememberLazyListState()
    val isConfirmationDialogVisible = remember { mutableStateOf(false) }
    val isTopBarExpanded = remember {  mutableStateOf(true) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> index > 0 }
            .filter { it }
            .collect {
                isTopBarExpanded.value = it.not()
            }
    }

    Surface {
        Box {
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                TopAppBarBillDetails(
                    isExpanded = isTopBarExpanded.value,
                    billName = uiState.billName,
                    uiState.totalValueFormatted,
                    uiState.spentByPerson,
                    onArrowBackClicked = { onNavigationIntent(NavigateBack) },
                    onEditClicked = {},
                    onExpandClicked = { isTopBarExpanded.value = true },
                    onCloseBillClicked = { onNavigationIntent(NavigateToCloseBill(billId = -1))},
                )
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(PaddingTwo),
                    contentPadding = PaddingValues(all = PaddingTwo),
                    modifier = Modifier.fillMaxSize(),
                ) {
                        items(
                            items = uiState.items,
                            key = {
                                it.itemId
                            }
                        ) { item: ConsumedItemModel ->
                            BillItem(
                                onRemoveClicked = {
                                    isConfirmationDialogVisible.value = true
                                },
                                item = item,
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.size(PaddingFive))
                        }
                    }
                ConfirmationDialog(
                    isVisible = isConfirmationDialogVisible.value,
                    title = "Deseja excluir esse item?",
                    message = "Essa ação não pode ser desfeita",
                    onDismiss = { isConfirmationDialogVisible.value = false },
                    onConfirm = { isConfirmationDialogVisible.value = false },
                )
            }
            FloatingActionButton(
                onClick = { onNavigationIntent(NavigateToCreateItem(billId = uiState.billId)) },
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(end = PaddingTwo, bottom = PaddingThree)
            ) {
                Icon(Icons.Rounded.Add, "Adicionar uma item")
            }
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
    )
)