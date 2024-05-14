package com.geanbrandao.br.billbuddy.presentation.createitem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.domain.model.CreateItemModel
import com.geanbrandao.br.billbuddy.domain.model.CreateItemModel.DividedValue
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingHalf
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne

@Composable
fun ResumeDialog(
    resume: CreateItemModel,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ResumeDialogView(
        resume = resume,
        onDismissRequest = onDismissRequest,
        onConfirm = onConfirm,
        modifier = modifier,
    )
}

@Composable
private fun ResumeDialogView(
    modifier: Modifier = Modifier,
    resume: CreateItemModel = CreateItemModel(
        itemName = "Coca-Cola",
        itemValue = 0f,
        billId = 1,
        dividedValues = emptyList(),
    ),
    onDismissRequest: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {


    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Salvar", style = MaterialTheme.typography.labelLarge)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Cancelar", style = MaterialTheme.typography.labelLarge)
            }
        },
        title = {
            Text(
                text = "Resumo da divisão",
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        text = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = resume.itemName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = resume.itemValueFormatted,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.size(size = PaddingOne))
                resume.dividedValues.forEach { item: DividedValue ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = PaddingHalf),
                    ) {
                        Text(text = item.personName)
                        Text(text = item.dividedValueFormatted)
                    }
                }
            }
        },
    )
}

@Preview
@Composable
private fun ResumeDialogPreview() {
    BillBuddyTheme {
        val resume = CreateItemModel(
            itemName = "Coca-Cola",
            itemValue = 5f,
            billId = 2,
            dividedValues = listOf(
                DividedValue(
                    personId = 1,
                    dividedValue = 2.5f,
                    personName = "Pessoa 1",
                ),
                DividedValue(
                    personId = 2,
                    dividedValue = 2.5f,
                    personName = "Pessoa 2",
                ),
            )
        )
        ResumeDialogView(resume = resume)
    }
}