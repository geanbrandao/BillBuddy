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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme

@Composable
fun CustomTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    onArrowBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    actionIcon: Painter? = null,
    actionIconContentDescription: String? = null,
    onActionClicked: () -> Unit = {},
) {
    CustomTopAppBarView(
        modifier = modifier,
        title = title,
        canNavigateBack = canNavigateBack,
        onArrowBackClicked = onArrowBackClicked,
        actionIcon = actionIcon,
        actionIconContentDescription = actionIconContentDescription,
        onActionClicked = onActionClicked,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTopAppBarView(
    modifier: Modifier = Modifier,
    title: String = "Screen Name",
    canNavigateBack: Boolean = true,
    onArrowBackClicked: () -> Unit = {},
    actionIcon: Painter? = null,
    actionIconContentDescription: String? = null,
    onActionClicked: () -> Unit = {},
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
                        contentDescription = "Ícone de voltar",
                    )
                }
            }
        } else {
            {}
        },
        actions = {
            ActionButton(
                actionIcon = actionIcon,
                actionIconContentDescription = actionIconContentDescription,
                onActionClicked = onActionClicked,
            )
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
fun ActionButton(
    actionIcon: Painter?,
    actionIconContentDescription: String?,
    onActionClicked: () -> Unit,
) {
    if (actionIcon != null) {
        IconButton(onClick = onActionClicked) {
            Icon(painter = actionIcon, contentDescription = actionIconContentDescription)
        }
    }
}

@Composable
@Preview
private fun TopAppBarPreview() {
    BillBuddyTheme {
        Scaffold(
            topBar = {
                CustomTopAppBarView()
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