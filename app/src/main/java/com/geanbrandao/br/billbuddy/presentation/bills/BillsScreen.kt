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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.presentation.bills.BillsNavigationIntent.NavigateToBill
import com.geanbrandao.br.billbuddy.presentation.common.ConfirmationDialog
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo
import org.koin.androidx.compose.koinViewModel

@Composable
fun BillsScreen(
    viewModel: BillsViewModel = koinViewModel()
) {
    BillsView(
        onNavigationIntent = viewModel::handleNavigation,
    )
}

@Composable
private fun BillsView(
    modifier: Modifier = Modifier,
    onNavigationIntent: (BillsNavigationIntent) -> Unit = {}
) {
    val isVisible = remember { mutableStateOf(false) }
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
            items(listOf(1, 2, 3, 4, 5, 6)) {
                BillItem(
                    modifier = Modifier.padding(vertical = PaddingOne),
                    onRemoveClicked = {
                        isVisible.value = true
                    },
                    onItemClicked = {
                        onNavigationIntent(NavigateToBill(id = it))
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
            isVisible = isVisible.value,
            title = "",
            message = "",
            onDismiss = { isVisible.value = false },
            onConfirm = { isVisible.value = false },
        )
    }
}

@Preview
@Composable
private fun BillsPreview() {
    BillBuddyTheme {
        BillsView()
    }
}