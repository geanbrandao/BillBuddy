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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo

@Composable
fun BillsScreen() {
    BillsView()
}

@Composable
private fun BillsView(
    modifier: Modifier = Modifier
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
                )
            }
        }
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PaddingTwo)
        ) {
            Text(text = "Criar conta")
        }
        ConfirmationDialog(
            isVisible = isVisible.value,
            onDismiss = { isVisible.value = false },
            onConfirm = { isVisible.value = false },
        )
    }
}

@Composable
private fun ConfirmationDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    if (isVisible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(text = "Excluir")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "Cancelar")
                }
            },
            title = {
                Text(
                    text = "Deseja excluir essa conta?",
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            text = {
                Text(
                    text = "Todas os dados e pessoas dessa conta serão removidos.",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
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