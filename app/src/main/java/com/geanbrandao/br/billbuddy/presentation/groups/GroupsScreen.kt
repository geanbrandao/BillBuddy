package com.geanbrandao.br.billbuddy.presentation.groups

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.presentation.common.BaseScreen
import com.geanbrandao.br.billbuddy.presentation.common.CustomTopAppBar
import com.geanbrandao.br.billbuddy.presentation.common.PrimaryButton
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingFour
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo
import org.koin.androidx.compose.koinViewModel

@Composable
fun GroupsScreen(
    viewModel: GroupsViewModel = koinViewModel(),
) {
    GroupsView()
}

@Composable
private fun GroupsView(
    modifier: Modifier = Modifier,
) {
    BaseScreen(
        modifier = modifier,
        header = {
            Header(onArrowBackClicked = { TODO("not implemented yet") })
        },
        content = {
            // ver uma forma de listar usuários que já existem
        },
        fixedFooter = {
            PrimaryButton(
                onClick = {},
                text = "Criar grupo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PaddingTwo)
                    .padding(bottom = PaddingFour),
            )
        },
    )
}

@Composable
private fun Header(
    onArrowBackClicked: () -> Unit,
) {
    CustomTopAppBar(
        title = stringResource(id = R.string.screen_groups_top_bar_title),
        canNavigateBack = true,
        onArrowBackClicked = onArrowBackClicked,
    )
}

@Preview
@Composable
private fun GroupsPreview() {
    BillBuddyTheme {
        GroupsView()
    }
}