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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.geanbrandao.br.billbuddy.presentation.createitem.components.ItemDivideBy
import com.geanbrandao.br.billbuddy.presentation.createitem.components.ItemNameInput
import com.geanbrandao.br.billbuddy.presentation.createitem.components.ItemValueInput
import com.geanbrandao.br.billbuddy.presentation.createitem.components.ResumeDialog
import com.geanbrandao.br.billbuddy.presentation.createitem.intents.CreateItemIntent
import com.geanbrandao.br.billbuddy.presentation.createitem.intents.CreateItemIntent.OnConfirmationDialog
import com.geanbrandao.br.billbuddy.presentation.createitem.intents.CreateItemIntent.OnCreateItem
import com.geanbrandao.br.billbuddy.presentation.createitem.intents.CreateItemIntent.OnNameChange
import com.geanbrandao.br.billbuddy.presentation.createitem.intents.CreateItemIntent.OnPersonChecked
import com.geanbrandao.br.billbuddy.presentation.createitem.intents.CreateItemIntent.OnValueChange
import com.geanbrandao.br.billbuddy.presentation.createitem.intents.CreateItemNavigationIntent
import com.geanbrandao.br.billbuddy.presentation.createitem.state.CreateItemUiState
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingFour
import com.geanbrandao.br.billbuddy.ui.theme.PaddingThree
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateItemScreen(
    viewModel: CreateItemViewModel = koinViewModel()
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getCreateItem()
        }
    }

    val uiState = viewModel.uiState.collectAsState()

    CreateItemView(
        onNavigationIntent = viewModel::handleNavigation,
        onCreateItemIntent = viewModel::handleIntents,
        uiState = uiState.value,
    )
}

@Composable
private fun CreateItemView(
    modifier: Modifier = Modifier,
    uiState: CreateItemUiState = CreateItemUiState(),
    onNavigationIntent: (CreateItemNavigationIntent) -> Unit = {},
    onCreateItemIntent: (CreateItemIntent) -> Unit = {},
) {
    val scrollableState = rememberScrollState()

    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollableState)
                .padding(horizontal = PaddingTwo),
            verticalArrangement = Arrangement.spacedBy(space = PaddingTwo)
        ) {
            Spacer(modifier = Modifier.size(PaddingTwo))
            ItemNameInput(
                text = uiState.name,
                onTextChange = { text -> onCreateItemIntent(OnNameChange(text)) },
                modifier = Modifier.fillMaxWidth()
            )
            ItemValueInput(
                text = uiState.value,
                onTextChange = { text ->
                    onCreateItemIntent(OnValueChange(text))
                },
                Modifier.fillMaxWidth()
            )
            ItemDivideBy(
                modifier = modifier,
                updateCheckedState = { isChecked: Boolean, id: Long ->
                    onCreateItemIntent(OnPersonChecked(isChecked = isChecked, id = id))
                },
                onSelectedAll = {
                    onCreateItemIntent(CreateItemIntent.OnDivideByAll)
                },
                list = uiState.persons,
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onCreateItemIntent(OnConfirmationDialog(isOpen = true))
                },
                enabled = uiState.isEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = PaddingFour, top = PaddingThree)
            ) {
                Text(text = "Salvar")
            }
            if (uiState.isConfirmationDialogOpen) {
                ResumeDialog(
                    resume = uiState.resume,
                    onDismissRequest = {
                        onCreateItemIntent(OnConfirmationDialog(isOpen = false))
                    },
                    onConfirm = {
                        onCreateItemIntent(OnConfirmationDialog(isOpen = false))
                        onCreateItemIntent(OnCreateItem)
                    }
                )
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