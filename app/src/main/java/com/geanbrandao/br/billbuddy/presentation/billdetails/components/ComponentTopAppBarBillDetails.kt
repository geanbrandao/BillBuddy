package com.geanbrandao.br.billbuddy.presentation.billdetails.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
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
import com.geanbrandao.br.billbuddy.domain.model.SpentByPersonModel
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingFour
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingThree
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo
import com.geanbrandao.br.billbuddy.ui.theme.TopAppBarHeight

@Composable
fun TopAppBarBillDetails(
    isExpanded: Boolean,
    billName: String,
    totalValue: String,
    items: List<SpentByPersonModel>,
    onArrowBackClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onExpandClicked: () -> Unit,
    onCloseBillClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBarBillDetailsView(
        isExpanded = isExpanded,
        onArrowBackClicked = onArrowBackClicked,
        onEditClicked = onEditClicked,
        onExpandClicked = onExpandClicked,
        onCloseBillClicked = onCloseBillClicked,
        billName = billName,
        totalValue = totalValue,
        persons = items,
        modifier = modifier,
    )
}

@Composable
private fun TopAppBarBillDetailsView(
    modifier: Modifier = Modifier,
    billName: String = "",
    totalValue: String = "",
    persons: List<SpentByPersonModel> = listOf(),
    isExpanded: Boolean = true,
    onArrowBackClicked: () -> Unit = {},
    onEditClicked: () -> Unit = {},
    onExpandClicked: () -> Unit = {},
    onCloseBillClicked: () -> Unit = {},
) {
    val lazyRowState = rememberLazyListState()
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(height = TopAppBarHeight)
            ) {
                IconButton(onClick = onArrowBackClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Ícone de voltar",
                    )
                }
                Text(
                    text = billName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = PaddingThree),
                )
                if (isExpanded.not()) {
                    IconButton(onClick = onExpandClicked) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_expand),
                            contentDescription = "Ícone de voltar",
                        )
                    }
                }
                IconButton(onClick = onEditClicked) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Ícone de voltar",
                    )
                }
            }
            AnimatedVisibility(visible = false) {
                Column {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(PaddingOne),
                        contentPadding = PaddingValues(all = PaddingTwo),
                        state = lazyRowState,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(items = persons, key = { it.personId }) { person: SpentByPersonModel ->
                            PersonItem(person)
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = PaddingTwo)
                    ) {
                        Text(
                            text = "Valor da conta:",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = totalValue,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    }
                    Spacer(modifier = Modifier.size(PaddingTwo))
                    Button(
                        onClick = onCloseBillClicked,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = PaddingTwo, bottom = PaddingOne)
                    ) {
                        Text(text = "Fechar conta")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TopAppBarBillPreview() {
    BillBuddyTheme {
        val list = listOf(
            SpentByPersonModel(billId = 1, personId = 1, name = "Pessoa 1", totalSpent = 45.0f),
            SpentByPersonModel(billId = 1, personId = 2, name = "Pessoa 2", totalSpent = 55.0f),
            SpentByPersonModel(billId = 1, personId = 3, name = "Pessoa 3", totalSpent = 62.3f),
            SpentByPersonModel(billId = 1, personId = 4, name = "Pessoa 4", totalSpent = 31.4f),
        )
        Column(verticalArrangement = Arrangement.spacedBy(PaddingFour)) {
            TopAppBarBillDetailsView(
                billName = "Nome da conta",
                totalValue = "R$ 154,52",
                persons = list
            )
            TopAppBarBillDetailsView(
                isExpanded = false,
                billName = "Nome da conta",
                totalValue = "R$ 154,52",
                persons = list
            )
        }
    }
}
