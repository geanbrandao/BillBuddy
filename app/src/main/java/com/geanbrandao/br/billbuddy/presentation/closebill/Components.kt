package com.geanbrandao.br.billbuddy.presentation.closebill

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.presentation.common.TextFieldInput
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
    TextFieldInput(
        modifier = modifier,
        text = text,
        label = "Digite o valor da taxa de serviço",
        leadingIcon = painterResource(id = R.drawable.ic_number),
        onTextChange = onTextChange,
    )
}

@Preview
@Composable
private fun ServiceFeeInputPreview() {
    BillBuddyTheme {
        ServiceFeeInputView(
            text = "0%",
        )
    }
}