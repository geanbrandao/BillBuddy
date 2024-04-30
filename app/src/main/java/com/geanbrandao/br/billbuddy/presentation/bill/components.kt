package com.geanbrandao.br.billbuddy.presentation.bill

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.CornerSizeHalf
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
    text: String = "",
    onTextChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val textFieldValue = remember { TextFieldValue(text = text) }
    TextField(
        value = textFieldValue,
        onValueChange = { onTextChange(it.text) },
        modifier = modifier,
        singleLine = true,
        label = { Text(text = "Nome da conta") },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_text),
                contentDescription = null,
            )
        }
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
    text: String = "",
    onTextChange: (String) -> Unit = {},
    onAddPerson: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val textFieldValue = remember { TextFieldValue(text = text) }
    TextField(
        value = textFieldValue,
        onValueChange = { onTextChange(it.text) },
        modifier = modifier,
        singleLine = true,
        label = { Text(text = "Quem vai dividir?") },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = null,
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "Adicionar pessoa",
                modifier = Modifier.clickable {
                    onAddPerson()
                }
            )
        }
    )
}

@Composable
fun PersonsList(
    list: List<String>,
    onRemoveClicked: () -> Unit,
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
    list: List<String> = listOf("Pessoa 1", "Pessoa 2", "Pessoa 3"),
    onRemoveClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = "Quem está dividindo a conta?",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(vertical = PaddingTwo)
            )
        }
        items(list, key = { it }) {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .padding(all = 1.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(CornerSizeHalf)
                    )
                    .padding(PaddingOne)
            ) {
                Text(
                    text = it,
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
                    modifier = Modifier.clickable { onRemoveClicked() }
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