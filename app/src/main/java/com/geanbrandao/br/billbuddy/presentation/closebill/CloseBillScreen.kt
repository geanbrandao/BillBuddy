package com.geanbrandao.br.billbuddy.presentation.closebill

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.CornerSizeOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingThree
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo

@Composable
fun CloseBillScreen() {
    CloseBillView()
}

@Composable
private fun CloseBillView(
    modifier: Modifier = Modifier,
    list: List<String> = listOf("Pessoa 1", "Pessoa 2", "Pessoa 3")
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = PaddingTwo),
            verticalArrangement = Arrangement.spacedBy(space = PaddingTwo)
        ) {
            Spacer(modifier = Modifier.size(size = PaddingThree))
            ServiceFeeInput(
                modifier = Modifier.fillMaxWidth(),
                text = "0%",
                onTextChange = {},
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(PaddingTwo),
                contentPadding = PaddingValues(all = PaddingTwo),
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(CornerSizeOne),
                )
            ) {
                items(list) {
                    UserSpentItem(
                        text = it,
                    )
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = PaddingTwo),
                    ) {
                        Text(
                            text = "Valor total da conta:",
                            style = MaterialTheme.typography.bodyLarge,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                        Text(
                            text = "R$ 300,00",
                            style = MaterialTheme.typography.bodyLarge,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = PaddingThree, top = PaddingTwo),
                onClick = { },
            ) {
                Text(text = "Compartilhar")
            }
        }
    }
}

@Composable
private fun UserSpentItem(
    modifier: Modifier = Modifier,
    text: String = "Pessoa 1",
    value: String = "R$ 100,00"
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
private fun CloseBillPreview() {
    BillBuddyTheme {
        CloseBillView()
    }
}