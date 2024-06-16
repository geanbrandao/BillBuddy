package com.geanbrandao.br.billbuddy.presentation.groups

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
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
    Surface {
        Column(modifier = modifier.fillMaxSize()) {
            Text("Grupos")
        }
    }
}

@Preview
@Composable
private fun GroupsPreview() {
    BillBuddyTheme {
        GroupsView()
    }
}