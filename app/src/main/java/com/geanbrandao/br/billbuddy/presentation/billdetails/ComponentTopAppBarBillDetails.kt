package com.geanbrandao.br.billbuddy.presentation.billdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingThree
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo
import com.geanbrandao.br.billbuddy.ui.theme.TopAppBarHeight

@Composable
fun TopAppBarBillDetails(
    isVisible: Boolean,
    onArrowBackClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onCloseBillClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBarBillDetailsView(
        isVisible = isVisible,
        onArrowBackClicked = onArrowBackClicked,
        onEditClicked = onEditClicked,
        onCloseBillClicked = onCloseBillClicked,
        modifier = modifier,
    )
}

@Composable
private fun TopAppBarBillDetailsView(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    onArrowBackClicked: () -> Unit = {},
    onEditClicked: () -> Unit = {},
    onCloseBillClicked: () -> Unit = {},
) {
    val lazyRowState = rememberLazyListState()
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(height = TopAppBarHeight)
            ) {
                IconButton(onClick = onArrowBackClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Ícone de voltar",
                    )
                }
                Text(
                    text = "Nome da conta",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = PaddingThree),
                )
                IconButton(onClick = onEditClicked) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Ícone de voltar",
                    )
                }
            }
            AnimatedVisibility(visible = isVisible) {
                Column {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(PaddingOne),
                        contentPadding = PaddingValues(all = PaddingTwo),
                        state = lazyRowState,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(listOf("Pessoa 1", "Pessoa 2", "Pessoa 3", "Pessoa 4", "Pessoa 5")) {
                            PersonItem()
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = PaddingTwo)
                    ) {
                        Text(
                            text = "Valor da conta:",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "R$ 154,34",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    }
                    Spacer(modifier = Modifier.size(PaddingTwo))
                    Button(
                        onClick = onCloseBillClicked,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = PaddingTwo)
                    ) {
                        Text(text = "Fechar conta")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TopAppBarBillPreview() {
    BillBuddyTheme {
        TopAppBarBillDetailsView()
    }
}
