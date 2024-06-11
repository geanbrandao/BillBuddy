package com.geanbrandao.br.billbuddy.presentation.closebill

import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.domain.model.SpentByPersonModel
import com.geanbrandao.br.billbuddy.presentation.closebill.components.PersonSpentItem
import com.geanbrandao.br.billbuddy.presentation.closebill.components.ServiceFeeInput
import com.geanbrandao.br.billbuddy.presentation.closebill.intents.CloseBillIntent
import com.geanbrandao.br.billbuddy.presentation.closebill.intents.CloseBillIntent.OnServiceTaxPercentageChange
import com.geanbrandao.br.billbuddy.presentation.closebill.intents.CloseBillIntent.OnShareBillResume
import com.geanbrandao.br.billbuddy.presentation.closebill.intents.CloseBillNavigationIntent
import com.geanbrandao.br.billbuddy.presentation.closebill.intents.CloseBillNavigationIntent.NavigateBack
import com.geanbrandao.br.billbuddy.presentation.closebill.state.CloseBillUiState
import com.geanbrandao.br.billbuddy.presentation.common.BaseScreen
import com.geanbrandao.br.billbuddy.presentation.common.CustomTopAppBar
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.CornerSizeOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CloseBillScreen(
    // todo fazer a tela scrollar igual na tela de criar item
    viewModel: CloseBillViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getCloseBill()
        }
    }
    val uiState = viewModel.uiState.collectAsState()

    CloseBillView(
        uiState = uiState.value,
        onIntent = viewModel::handleIntent,
        onNavigationIntent = viewModel::handleNavigation,
    )
}

@Composable
private fun CloseBillView(
    modifier: Modifier = Modifier,
    uiState: CloseBillUiState = CloseBillUiState(),
    onIntent: (CloseBillIntent) -> Unit = {},
    onNavigationIntent: (CloseBillNavigationIntent) -> Unit = {},
) {
    val graphicsLayer = rememberGraphicsLayer()
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.uri) {
        uiState.uri?.let {
            shareBillResume(it, context)
        }
    }

    BaseScreen(
        header = { Header(onNavigationIntent, graphicsLayer, onIntent) },
        content = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = PaddingTwo),
                verticalArrangement = Arrangement.spacedBy(space = PaddingTwo)
            ) {
                Spacer(modifier = Modifier.size(size = PaddingTwo))
                ServiceFeeInput(
                    modifier = Modifier.fillMaxWidth(),
                    text = uiState.serviceFeeFormatted,
                    onTextChange = { onIntent(OnServiceTaxPercentageChange(value = it)) },
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(PaddingTwo),
                    contentPadding = PaddingValues(all = PaddingTwo),
                    modifier = Modifier
                        .drawWithContent {
                            graphicsLayer.record {
                                this@drawWithContent.drawContent()
                            }
                            drawLayer(graphicsLayer)
                        }
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = RoundedCornerShape(CornerSizeOne),
                        )
                ) {
                    items(
                        items = uiState.spentByPersonList,
                        key = {
                            it.personId
                        }
                    ) { item: SpentByPersonModel ->
                        PersonSpentItem(
                            header = item.name,
                            label = item.getTotalSpentDetails(uiState.serviceFee),
                            info = item.totalSpentWithServiceTaxFormatted(uiState.serviceFee),
                            modifier = modifier,
                        )
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(top = PaddingTwo),
                        ) {
                            Text(
                                text = "Valor total da taxa de serviço:",
                                style = MaterialTheme.typography.bodyMedium,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                            )
                            Text(
                                text = uiState.totalServiceFeeFormatted,
                                style = MaterialTheme.typography.bodyMedium,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = modifier
                                .fillMaxWidth(),
                        ) {
                            Text(
                                text = "Valor total da conta:",
                                style = MaterialTheme.typography.bodyLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                            )
                            Text(
                                text = uiState.totalSpentServiceFeeFormatted,
                                style = MaterialTheme.typography.bodyLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun Header(
    onNavigationIntent: (CloseBillNavigationIntent) -> Unit,
    graphicsLayer: GraphicsLayer,
    onIntent: (CloseBillIntent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    CustomTopAppBar(
        title = stringResource(id = R.string.screen_close_bill_top_bar_title),
        canNavigateBack = true,
        onArrowBackClicked = { onNavigationIntent(NavigateBack) },
        actionIcon = painterResource(id = R.drawable.ic_share),
        actionIconContentDescription = "Compartilhar resumo da conta",
        onActionClicked = {
            coroutineScope.launch {
                val bitmap = graphicsLayer.toImageBitmap()
                onIntent(OnShareBillResume(bitmap = bitmap.asAndroidBitmap()))
            }
        },
    )
}

private fun shareBillResume(uri: Uri, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(
        context,
        createChooser(intent, "Compatilhar o resumo da conta"),
        null
    )
}

@Preview
@Composable
private fun CloseBillPreview() {
    BillBuddyTheme {
        val uiState = CloseBillUiState(
            billId = 1,
            serviceFeeFormatted = "0,00",
            spentByPersonList = listOf(
                SpentByPersonModel(
                    billId = 1,
                    personId = 1,
                    name = "João",
                    totalSpent = 100f,
                ),
                SpentByPersonModel(
                    billId = 1,
                    personId = 2,
                    name = "John",
                    totalSpent = 200f,
                ),
                SpentByPersonModel(
                    billId = 1,
                    personId = 3,
                    name = "John",
                    totalSpent = 300f,
                )
            )
        )
        CloseBillView(uiState = uiState)
    }
}