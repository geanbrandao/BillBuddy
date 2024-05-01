package com.geanbrandao.br.billbuddy.presentation.billdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.geanbrandao.br.billbuddy.presentation.common.ConfirmationDialog
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingThree
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo

@Composable
fun BillDetailsScreen() {
    BillDetailsView()
}

@Composable
private fun BillDetailsView(
    modifier: Modifier = Modifier,
    onAddItem: () -> Unit = {},
) {
    val listState = rememberLazyListState()
    val isConfirmationDialogVisible = remember { mutableStateOf(false) }
    val isScrollingUp by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    Surface {
        Box {
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                TopAppBarBillDetails(
                    isVisible = isScrollingUp.not(),
                    onArrowBackClicked = { /*TODO*/ },
                    onEditClicked = {  }
                )
                val range = 1..10
                range.let {

                }
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(PaddingTwo),
                    contentPadding = PaddingValues(all = PaddingTwo),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(range.count()) {
                        BillItem(
                            onRemoveClicked = {
                                isConfirmationDialogVisible.value = true
                            },
                            item = "Nome do item $it"
                        )
                    }
                }
                
                ConfirmationDialog(
                    isVisible = isConfirmationDialogVisible.value,
                    title = "",
                    message = "",
                    onDismiss = { isConfirmationDialogVisible.value = false },
                    onConfirm = { isConfirmationDialogVisible.value = false },
                )
            }
            FloatingActionButton(
                onClick = { onAddItem() },
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(end = PaddingTwo, bottom = PaddingThree)
            ) {
                Icon(Icons.Rounded.Add, "Adicionar uma item")
            }
        }
    }
}

//@Composable
//private fun LazyListState.isScrollingUp(): Boolean {
//    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
//    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
//    return remember(this) {
//        derivedStateOf {
//            if (previousIndex != firstVisibleItemIndex) {
//                previousIndex > firstVisibleItemIndex
//            } else {
//                previousScrollOffset >= firstVisibleItemScrollOffset
//            }.also {
//                previousIndex = firstVisibleItemIndex
//                previousScrollOffset = firstVisibleItemScrollOffset
//            }
//        }
//    }.value
//}

//@Composable
//fun LazyListState.isScrollingUp(): State<Boolean> {
//    return produceState(initialValue = true) {
//        var lastIndex = 0
//        var lastScroll = Int.MAX_VALUE
//        snapshotFlow {
//            firstVisibleItemIndex to firstVisibleItemScrollOffset
//        }.collect { (currentIndex, currentScroll) ->
//            if (currentIndex != lastIndex || currentScroll != lastScroll) {
//                value = currentIndex < lastIndex ||
//                        (currentIndex == lastIndex && currentScroll < lastScroll)
//                lastIndex = currentIndex
//                lastScroll = currentScroll
//            }
//        }
//    }
//}

@Preview
@Composable
private fun BillDetailsPreview() {
    BillBuddyTheme {
        BillDetailsView()
    }
}