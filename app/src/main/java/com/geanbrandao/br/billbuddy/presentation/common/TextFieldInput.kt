package com.geanbrandao.br.billbuddy.presentation.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme

@Composable
fun TextFieldInput(
    text: String,
    label: String,
    leadingIcon: Painter,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    trailingIcon: Painter? = null,
    trailingIconContentDescription: String? = null,
    onTrailingIconClicked: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences,
        keyboardType = KeyboardType.Text,
    )
) {
    TextFieldInputView(
        modifier = modifier,
        text = text,
        label = label,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        trailingIconContentDescription = trailingIconContentDescription,
        onTrailingIconClicked = onTrailingIconClicked,
        onTextChange = onTextChange,
        keyboardOptions = keyboardOptions,
    )
}

@Composable
fun TextFieldInputView(
    modifier: Modifier = Modifier,
    text: String = "",
    label: String = "",
    leadingIcon: Painter = painterResource(id = R.drawable.ic_text),
    trailingIcon: Painter? = null,
    trailingIconContentDescription: String? = null,
    onTrailingIconClicked: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences,
        keyboardType = KeyboardType.Text,
    )
) {
    val textFieldValue = remember { TextFieldValue(text = text) }
    TextField(
        value = textFieldValue,
        onValueChange = { onTextChange(it.text) },
        modifier = modifier,
        singleLine = true,
        label = { Text(text = label) },
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

@Composable
fun TrailingIcon(
    painter: Painter,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
        )
    }
}

@Preview
@Composable
private fun TextFieldInputPreview() {
    BillBuddyTheme {
        TextFieldInputView(
            text = "",
            label = "Digite o texto",
        )
    }
}

