package com.geanbrandao.br.billbuddy.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarView(
    title: String,
    canNavigateBack: Boolean,
    onArrowBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        navigationIcon = if (canNavigateBack) {
            {
                IconButton(onClick = onArrowBackClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        } else {
            {}
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
@Preview
private fun TopAppBarPreview() {
    BillBuddyTheme {
        Scaffold(
            topBar = {
                TopAppBarView(
                    title = "TopAppBar",
                    canNavigateBack = false,
                    onArrowBackClicked = {}
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
            ) {
                items((1..150).toList()) {
                    Text(text = "Item $it")
                }
            }
        }
    }
}