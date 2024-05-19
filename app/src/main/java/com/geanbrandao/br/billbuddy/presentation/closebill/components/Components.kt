package com.geanbrandao.br.billbuddy.presentation.closebill.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.presentation.common.TextFieldInputFormatted
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme

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

@Preview
@Composable
private fun ServiceFeeInputPreview() {
    BillBuddyTheme {
        ServiceFeeInputView(
            text = "10",
        )
    }
}