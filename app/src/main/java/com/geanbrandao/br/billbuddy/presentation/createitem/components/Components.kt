package com.geanbrandao.br.billbuddy.presentation.createitem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.domain.model.PersonModel
import com.geanbrandao.br.billbuddy.presentation.common.TextFieldInput
import com.geanbrandao.br.billbuddy.presentation.common.TextFieldInputFormatted
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.CheckboxRowHeight
import com.geanbrandao.br.billbuddy.ui.theme.PaddingFive
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo

@Composable
fun ItemNameInput(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ItemNameInputView(
        modifier = modifier,
        text = text,
        onTextChange = onTextChange,
    )
}

@Composable
private fun ItemNameInputView(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChange: (String) -> Unit = {},
) {
    TextFieldInput(
        text = text,
        label = "Digite o nome do item",
        leadingIcon = painterResource(id = R.drawable.ic_text),
        onTextChange = onTextChange,
        keyboardOptions = KeyboardOptions(KeyboardCapitalization.Sentences),
        modifier = modifier,
    )
}

@Composable
fun ItemValueInput(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ItemValueInputView(
        modifier = modifier,
        text = text,
        onTextChange = onTextChange,
    )
}

@Composable
private fun ItemValueInputView(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChange: (String) -> Unit = {},
) {
    TextFieldInputFormatted(
        modifier = modifier,
        text = text,
        onTextChange = onTextChange,
        label = "Digite o valor do item",
        prefix = "R$",
        leadingIcon = painterResource(id = R.drawable.ic_money),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}

@Composable
fun ItemDivideBy(
    list: List<PersonModel>,
    updateCheckedState: (Boolean, Long) -> Unit,
    onSelectedAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ItemDivideByView(
        modifier = modifier,
        list = list,
        updateCheckedState = updateCheckedState,
        onSelectedAll = onSelectedAll,
    )
}

@Composable
private fun ItemDivideByView(
    modifier: Modifier = Modifier,
    list: List<PersonModel> = listOf(),
    updateCheckedState: (Boolean, Long) -> Unit = { _, _ -> },
    onSelectedAll: () -> Unit = {},
) {
    val checkStateList = list.map { it.isChecked }
    val triStateCheckboxState = remember { mutableStateOf(ToggleableState.Off) }
    LaunchedEffect(key1 = checkStateList) {
        triStateCheckboxState.value = if (checkStateList.all { it }) {
            ToggleableState.On
        } else if (checkStateList.any { it }) {
            ToggleableState.Indeterminate
        } else {
            ToggleableState.Off
        }
    }

    Column(
        modifier = modifier,
    ) {
        Text(
            text = "Escolha uma ou mais pessoas",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(CheckboxRowHeight)
                .fillMaxWidth(),
        ) {
            TriStateCheckbox(
                state = triStateCheckboxState.value,
                onClick = onSelectedAll,
            )
            Text(
                text = "Divir entre todos",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = PaddingTwo)
            )
        }
        Column(
            Modifier.padding(start = PaddingFive)
        ) {
            list.forEachIndexed { index, item ->
                CheckBoxItem(
                    text = item.name,
                    checkedState = checkStateList[index],
                    onStateChange = { isChecked: Boolean ->
                        updateCheckedState(isChecked, item.id)
                    }
                )
            }
        }
    }
}

@Composable
fun CheckBoxItem(
    text: String,
    checkedState: Boolean,
    onStateChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(CheckboxRowHeight)
            .toggleable(
                value = checkedState,
                onValueChange = {
                    onStateChange(!checkedState)
                },
                role = Role.Checkbox
            )
    ) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = null
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(start = PaddingTwo)
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview
@Composable
private fun ItemNameInputPreview() {
    BillBuddyTheme {
        ItemNameInputView()
    }
}

@Preview
@Composable
private fun ItemValueInputPreview() {
    BillBuddyTheme {
        ItemValueInputView()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ItemDivideByPreview() {
    BillBuddyTheme {
        val list = remember {
            mutableStateOf(getListPerson())
        }
        ItemDivideByView(
            // todo criar caso de uso para cuidar dessa lógica e manter testavel
            updateCheckedState = { isChecked, id ->
                list.value = list.value.map { item ->
                    if (item.id == id) {
                        item.copy(isChecked = isChecked)
                    } else {
                        item
                    }
                }
            },
            // todo criar caso de uso para cuidar dessa lógica e manter testavel
            onSelectedAll = {
                val isChecked = list.value.all { it.isChecked }.not()
                list.value = list.value.map { item ->
                    item.copy(isChecked = isChecked)
                }
            },
            list = list.value
        )
    }
}

fun getListPerson() = listOf(
    PersonModel(id = 1, name = "João", isChecked = false),
    PersonModel(id = 2, name = "Pedro", isChecked = false),
    PersonModel(id = 3, name = "Maria", isChecked = false),
    PersonModel(id = 4, name = "Marta", isChecked = false),
)