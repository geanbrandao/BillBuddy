package com.geanbrandao.br.billbuddy.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo

@Composable
fun TextFieldInputFormatted(
    text: String,
    label: String,
    leadingIcon: Painter,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    prefix: String? = null,
    suffix: String? = null,
    trailingIcon: Painter? = null,
    trailingIconContentDescription: String? = null,
    onTrailingIconClicked: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences,
        keyboardType = KeyboardType.Text,
    )
) {
    TextFieldInputFormattedView(
        modifier = modifier,
        text = text,
        label = label,
        leadingIcon = leadingIcon,
        prefix = prefix,
        suffix = suffix,
        trailingIcon = trailingIcon,
        trailingIconContentDescription = trailingIconContentDescription,
        onTrailingIconClicked = onTrailingIconClicked,
        onTextChange = onTextChange,
        keyboardOptions = keyboardOptions,
    )
}

@Composable
fun TextFieldInputFormattedView(
    modifier: Modifier = Modifier,
    text: String = "1000",
    label: String = "",
    leadingIcon: Painter = painterResource(id = R.drawable.ic_text),
    prefix: String? = null,
    suffix: String? = null,
    trailingIcon: Painter? = null,
    trailingIconContentDescription: String? = null,
    onTrailingIconClicked: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences,
        keyboardType = KeyboardType.Text,
    )
) {
    val textFieldValue = TextFieldValue(text = text, selection = TextRange(text.length))
    TextField(
        value = textFieldValue,
        onValueChange = { onTextChange(it.text) },
        modifier = modifier,
        singleLine = true,
        label = { Text(text = label) },
        prefix = prefix?.let {
            { Text(text = it) }
        },
        suffix = suffix?.let {
            { Text(text = it) }
        },
        leadingIcon = {
            Icon(
                painter = leadingIcon,
                contentDescription = null,
            )
        },
        trailingIcon = trailingIcon?.let {
            {
                TrailingIcon(
                    painter = it,
                    contentDescription = trailingIconContentDescription,
                    onClick = onTrailingIconClicked,
                )
            }
        },
        keyboardOptions = keyboardOptions,
    )
}

@Preview(showBackground = true)
@Composable
private fun TextFieldInputFormattedPreview() {
    BillBuddyTheme {
        Column(
            modifier = Modifier.padding(PaddingTwo),
            verticalArrangement = Arrangement.spacedBy(space = PaddingTwo),
        ) {
            TextFieldInputFormattedView(
                text = "10",
                label = "Digite o texto",
                suffix = "%",
            )
            TextFieldInputFormattedView(
                text = "0,00",
                label = "Digite o texto",
                prefix = "R$"
            )
        }
    }
}
