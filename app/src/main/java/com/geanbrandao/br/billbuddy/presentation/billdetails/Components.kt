package com.geanbrandao.br.billbuddy.presentation.billdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.CornerSizeOne
import com.geanbrandao.br.billbuddy.ui.theme.ElevationOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingHalf
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo

@Composable
fun PersonItem(
    modifier: Modifier = Modifier
) {
    PersonItemView(modifier = modifier)
}

@Composable
private fun PersonItemView(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.tertiaryContainer,
        shape = RoundedCornerShape(CornerSizeOne),
    ) {
        Column(
            modifier = Modifier.padding(PaddingTwo),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(PaddingHalf)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_account),
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                contentDescription = "Ícone de voltar",
            )
            Text(
                text = "Pessoa 1",
                maxLines = 1,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
            )
            Text(
                text = "R$ 45,00",
                maxLines = 1,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
            )
        }
    }
}

@Composable
fun BillItem(
    item: String,
    onRemoveClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    BillItemView(
        modifier = modifier,
        onRemoveClicked = onRemoveClicked,
        item = item,
    )
}

@Composable
private fun BillItemView(
    modifier: Modifier = Modifier,
    item: String = "",
    onRemoveClicked: () -> Unit = {},
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = RoundedCornerShape(CornerSizeOne),
        shadowElevation = ElevationOne,
        modifier = modifier,
    ) {
        Column(Modifier.padding(horizontal = PaddingTwo, vertical = PaddingOne).fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                IconButton(onClick = onRemoveClicked) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = "Remover conta",
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth().padding(top = PaddingOne)
            ) {
                Text(
                    text = "Pessoa 1",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "R$ 4,50",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}

@Preview
@Composable
private fun PersonItemPreview() {
    BillBuddyTheme {
        PersonItemView()
    }
}

@Preview
@Composable
private fun BillItemPreview() {
    BillBuddyTheme {
        BillItemView()
    }
}