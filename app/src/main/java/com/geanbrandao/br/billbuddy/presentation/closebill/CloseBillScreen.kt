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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.geanbrandao.br.billbuddy.domain.model.SpentByPersonModel
import com.geanbrandao.br.billbuddy.presentation.closebill.components.ServiceFeeInput
import com.geanbrandao.br.billbuddy.presentation.closebill.intents.CloseBillIntent
import com.geanbrandao.br.billbuddy.presentation.closebill.state.CloseBillUiState
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.CornerSizeOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingThree
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo
import org.koin.androidx.compose.koinViewModel

@Composable
fun CloseBillScreen(
    viewModel: CloseBillViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getCloseBill()
        }
    }
    val uiState = viewModel.uiState.collectAsState()

    CloseBillView(
        uiState = uiState.value,
        onIntent = viewModel::handleIntent,
    )
}

@Composable
private fun CloseBillView(
    modifier: Modifier = Modifier,
    uiState: CloseBillUiState = CloseBillUiState(),
    onIntent: (CloseBillIntent) -> Unit = {},
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = PaddingTwo),
            verticalArrangement = Arrangement.spacedBy(space = PaddingTwo)
        ) {
            Spacer(modifier = Modifier.size(size = PaddingOne))
            ServiceFeeInput(
                modifier = Modifier.fillMaxWidth(),
                text = uiState.serviceTaxPercentage,
                onTextChange = {onIntent(CloseBillIntent.OnServiceTaxPercentageChange(value = it)) },
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(PaddingTwo),
                contentPadding = PaddingValues(all = PaddingTwo),
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(CornerSizeOne),
                )
            ) {
                items(
                    items = uiState.spentByPerson,
                    key = {
                        it.personId
                    }
                ) { item: SpentByPersonModel ->
                    UserSpentItem(
                        item = item,
                        serviceTaxPercentageValue = uiState.serviceTaxPercentageNumber
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
                            text = uiState.totalSpentServiceTaxFormatted,
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
    item: SpentByPersonModel,
    serviceTaxPercentageValue: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        Column {
            Text(
                text = item.totalWithServiceTax(serviceTax = serviceTaxPercentageValue),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
            )
//            Text(
//                text = item.totalSpentFormatted,
//                style = MaterialTheme.typography.bodySmall,
//                overflow = TextOverflow.Ellipsis,
//                maxLines = 1,
//                modifier = Modifier.align(Alignment.End)
//            )
        }
    }
}

@Preview
@Composable
private fun CloseBillPreview() {
    BillBuddyTheme {
        val uiState = CloseBillUiState(
            billId = 1,
            serviceTaxPercentage = "10",
            spentByPerson = listOf(
                SpentByPersonModel(
                    billId = 1,
                    personId = 1,
                    name = "João",
                    totalSpent = 100f,
                ),
                SpentByPersonModel(
                    billId = 1,
                    personId = 2,
                    name = "John",
                    totalSpent = 200f,
                ),
                SpentByPersonModel(
                    billId = 1,
                    personId = 3,
                    name = "John",
                    totalSpent = 300f,
                )
            )
        )
        CloseBillView(uiState = uiState)
    }
}