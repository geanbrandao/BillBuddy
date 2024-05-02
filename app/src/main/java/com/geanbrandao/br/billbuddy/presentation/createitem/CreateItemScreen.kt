package com.geanbrandao.br.billbuddy.presentation.createitem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingFour
import com.geanbrandao.br.billbuddy.ui.theme.PaddingThree
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo

@Composable
fun CreateItemScreen() {
    CreateItemView(
        // uiState
        // intents
        // navigationActions
    )
}

@Composable
private fun CreateItemView(
    modifier: Modifier = Modifier,
) {
    val scrollableState = rememberScrollState()
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollableState)
                .padding(horizontal = PaddingTwo),
            verticalArrangement = Arrangement.spacedBy(PaddingTwo)
        ) {
            Spacer(modifier = Modifier.size(PaddingTwo))
            ItemNameInput(
                text = "",
                onTextChange = {},
                modifier = Modifier.fillMaxWidth()
            )
            ItemValueInput(text = "0,00", onTextChange = {}, Modifier.fillMaxWidth())
            val list = remember {
                mutableStateOf(getListPerson())
            }
            ItemDivideBy(
                modifier = modifier,
                updateCheckedState = { isChecked, index ->
                    list.value = list.value.mapIndexed { indexMap, item ->
                        if (indexMap == index) {
                            item.copy(isChecked = isChecked)
                        } else {
                            item
                        }
                    }
                },
                onSelectedAll = {
                    val isChecked = list.value.all { it.isChecked }.not()
                    list.value = list.value.map { item ->
                        item.copy(isChecked = isChecked)
                    }
                },
                list = list.value
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = PaddingFour, top = PaddingThree)
            ) {
                Text(text = "Salvar")
            }
        }
    }
}

@Preview
@Composable
private fun CreateItemPreview() {
    BillBuddyTheme {
        CreateItemView()
    }
}