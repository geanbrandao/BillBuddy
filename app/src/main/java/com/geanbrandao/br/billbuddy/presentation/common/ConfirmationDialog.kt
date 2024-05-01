package com.geanbrandao.br.billbuddy.presentation.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme

@Composable
fun ConfirmationDialog(
    isVisible: Boolean,
    title: String,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    ConfirmationDialogView(
        isVisible = isVisible,
        title = title,
        message = message,
        onDismiss = onDismiss,
        onConfirm = onConfirm,
    )
}


@Composable
private fun ConfirmationDialogView(
    isVisible: Boolean = true,
    title: String = "",
    message: String = "",
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
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
private fun ConfirmationDialogPreview() {
    BillBuddyTheme {
        ConfirmationDialogView()
    }
}