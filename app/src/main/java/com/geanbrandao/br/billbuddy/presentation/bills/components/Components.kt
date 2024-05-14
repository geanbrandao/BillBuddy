package com.geanbrandao.br.billbuddy.presentation.bills.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geanbrandao.br.billbuddy.domain.model.BillModel
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.CornerSizeOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo

@Composable
fun BillItem(
    billItem: BillModel,
    onRemoveClicked: () -> Unit,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BillItemView(
        modifier = modifier,
        billItem = billItem,
        onRemoveClicked = onRemoveClicked,
        onItemClicked = onItemClicked
    )
}

@Composable
private fun BillItemView(
    modifier: Modifier = Modifier,
    billItem: BillModel = BillModel(0, "Nome da conta", "Em aberto", "R$ 10,00"),
    onRemoveClicked: () -> Unit = {},
    onItemClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                role = Role.Button,
                onClick = onItemClicked
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(size = CornerSizeOne)
            )
            .padding(PaddingTwo),
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = billItem.name,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier
                .weight(1f)
                .padding(all = PaddingOne))
            IconButton(onClick = onRemoveClicked) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = "Remover conta",
                )
            }
        }
        Spacer(modifier = Modifier.size(PaddingTwo))
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Status:",
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier
                .weight(1f)
                .padding(all = PaddingOne))
            Text(
                text = billItem.status,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.size(PaddingTwo))
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Valor da conta:",
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier
                .weight(1f)
                .padding(all = PaddingOne))
            Text(
                text = billItem.total,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun BillItemPreview() {
    BillBuddyTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            BillItemView(modifier = Modifier.padding(vertical = PaddingOne))
            BillItemView(modifier = Modifier.padding(vertical = PaddingOne))
            BillItemView(modifier = Modifier.padding(vertical = PaddingOne))
        }
    }
}