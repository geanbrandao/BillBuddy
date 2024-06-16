package com.geanbrandao.br.billbuddy.presentation.billdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.geanbrandao.br.billbuddy.domain.model.ConsumedItemModel
import com.geanbrandao.br.billbuddy.domain.model.DividedValueModel
import com.geanbrandao.br.billbuddy.domain.model.SpentByPersonModel
import com.geanbrandao.br.billbuddy.presentation.common.SwipeLeft
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.CornerSizeOne
import com.geanbrandao.br.billbuddy.ui.theme.ElevationOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingHalf
import com.geanbrandao.br.billbuddy.ui.theme.PaddingThree
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo

@Composable
fun PersonItem(
    person: SpentByPersonModel,
    modifier: Modifier = Modifier
) {
    PersonItemView(modifier = modifier, person = person)
}

@Composable
private fun PersonItemView(
    modifier: Modifier = Modifier,
    person: SpentByPersonModel = SpentByPersonModel(billId = 1, personId = 1, name = "Pessoa 1", totalSpent = 45.0f),
) {
    Surface(
        modifier = modifier,
        shadowElevation = ElevationOne,
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
                text = person.name,
                maxLines = 1,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
            )
            Text(
                text = person.totalSpentFormatted,
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
    item: ConsumedItemModel,
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
    item: ConsumedItemModel = getConsumedItemModel(),
    onRemoveClicked: () -> Unit = {},
) {
    SwipeLeft(
        onDeleteClicked = onRemoveClicked,
        content = {
            Column(
                modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(CornerSizeOne),
                    )
                    .padding(all = PaddingTwo)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = item.formatedValue,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.size(PaddingTwo))
                if (item.dividedValues.size == 1) {
                    DividedByOne(item)
                } else {
                    DividedByMany(item)
                }
            }
        }
    )
}

@Composable
private fun DividedByOne(item: ConsumedItemModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = item.dividedValues.first().userName,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun DividedByMany(item: ConsumedItemModel) {
    Text(
        text = "Dividido entre ${item.dividedValues.size} pessoas",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.primary,
    )
    item.dividedValues.forEach { dividedValue ->
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = PaddingHalf)
        ) {
            Text(
                text = dividedValue.userName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = dividedValue.formatedValue,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PersonItemPreview() {
    BillBuddyTheme {
        Column(Modifier.padding(PaddingThree)) {
            PersonItemView()
        }
    }
}

@Preview
@Composable
private fun BillItemPreview() {
    BillBuddyTheme {
        BillItemView()
    }
}

private fun getConsumedItemModel() = ConsumedItemModel(
    itemId = 1,
    name = "Coca-cola",
    value = 10.0f,
    dividedValues = listOf(
        DividedValueModel(userId = 1, userName = "Pessoa 1", value = 5.0f),
        DividedValueModel(userId = 2, userName = "Pessoa 2", value = 5.0f),
    )
)