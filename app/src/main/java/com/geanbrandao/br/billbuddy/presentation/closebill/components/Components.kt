package com.geanbrandao.br.billbuddy.presentation.closebill.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.presentation.common.TextFieldInputFormatted
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo

@Composable
fun ServiceFeeInput(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ServiceFeeInputView(
        modifier = modifier,
        text = text,
        onTextChange = onTextChange,
    )
}

@Composable
private fun ServiceFeeInputView(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChange: (String) -> Unit = {},
) {
    TextFieldInputFormatted(
        modifier = modifier,
        text = text,
        label = "Porcentagem da taxa de serviço",
        suffix = "%",
        leadingIcon = painterResource(id = R.drawable.ic_number),
        onTextChange = onTextChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}

@Composable
fun PersonSpentItem(
    header: String,
    label: String,
    info: String,
    modifier: Modifier = Modifier
) {
    PersonSpentItemView(
        header = header,
        label = label,
        info = info,
        modifier = modifier,
    )
}

@Composable
private fun PersonSpentItemView(
    modifier: Modifier = Modifier,
    header: String = "",
    label: String = "",
    info: String = "",
) {
    val labelColor = MaterialTheme.colorScheme.onBackground
    Column(
        modifier = modifier,
    ) {
        Text(
            text = header,
            style = MaterialTheme.typography.labelLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = labelColor.copy(alpha = 0.8f),
            )
            Text(
                text = info,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}

@Preview
@Composable
private fun ServiceFeeInputPreview() {
    BillBuddyTheme {
        ServiceFeeInputView(
            modifier = Modifier.fillMaxWidth(),
            text = "10",
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PersonSpentItemPreview() {
    BillBuddyTheme {
        PersonSpentItemView(
            modifier = Modifier.fillMaxWidth().padding(all = PaddingTwo),
            header = "João",
            label = "R$ 100,00 + R$ 10,00",
            info = "R$ 110,00"
        )
    }
}