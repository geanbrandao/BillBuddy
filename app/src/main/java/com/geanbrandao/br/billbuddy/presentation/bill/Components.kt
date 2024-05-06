package com.geanbrandao.br.billbuddy.presentation.bill

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.presentation.common.TextFieldInput
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.CornerSizeHalf
import com.geanbrandao.br.billbuddy.ui.theme.PaddingHalf
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo

@Composable
fun BillNameInput(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BillNameInputView(
        modifier = modifier,
        text = text,
        onTextChange = onTextChange,
    )
}

@Composable
private fun BillNameInputView(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChange: (String) -> Unit = {},
) {

    TextFieldInput(
        text = text,
        modifier = modifier,
        label = "Nome da conta",
        leadingIcon = painterResource(id = R.drawable.ic_text),
        onTextChange = onTextChange,
    )
}

@Composable
fun PersonNameInput(
    text: String,
    onTextChange: (String) -> Unit,
    onAddPerson: () -> Unit,
    modifier: Modifier = Modifier
) {
    PersonNameInputView(
        modifier = modifier,
        text = text,
        onTextChange = onTextChange,
        onAddPerson = onAddPerson,
    )
}

@Composable
private fun PersonNameInputView(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChange: (String) -> Unit = {},
    onAddPerson: () -> Unit = {},
) {

    TextFieldInput(
        modifier = modifier,
        text = text,
        label ="Quem vai dividir?",
        leadingIcon = painterResource(id = R.drawable.ic_person),
        trailingIcon = painterResource(id = R.drawable.ic_check),
        trailingIconContentDescription = "Adicionar pessoa",
        onTrailingIconClicked = onAddPerson,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        onTextChange = onTextChange,
    )
}

@Composable
fun PersonsList(
    list: List<String>,
    onRemoveClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    PersonsListView(
        list = list,
        onRemoveClicked = onRemoveClicked,
        modifier = modifier,
    )
}

@Composable
private fun PersonsListView(
    modifier: Modifier = Modifier,
    list: List<String> = listOf("Pessoa 1", "Pessoa 2", "Pessoa 3"),
    onRemoveClicked: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(PaddingHalf)
    ) {
        item {
            Text(
                text = "Quem está dividindo a conta?",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(vertical = PaddingTwo)
            )
        }
        items(list, key = { it }) { personName: String ->
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(CornerSizeHalf),
                    )
                    .padding(PaddingOne)
            ) {
                Text(
                    text = personName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = "Remover conta",
                    modifier = Modifier.clickable { onRemoveClicked(personName) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun BillNameInputPreview() {
    BillBuddyTheme {
        BillNameInputView()
    }
}

@Preview
@Composable
private fun PersonNameInputPreview() {
    BillBuddyTheme {
        PersonNameInputView()
    }
}

@Preview
@Composable
private fun PersonListPreview() {
    BillBuddyTheme {
        PersonsListView()
    }
}