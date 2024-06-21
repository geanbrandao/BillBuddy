package com.geanbrandao.br.billbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.geanbrandao.br.billbuddy.domain.model.SnackbarType
import com.geanbrandao.br.billbuddy.navigation.NavigationView
import com.geanbrandao.br.billbuddy.navigation.Screen
import com.geanbrandao.br.billbuddy.presentation.common.WarningSnackbar
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.PaddingThree
import com.geanbrandao.br.billbuddy.ui.theme.PaddingTwo
import kotlinx.coroutines.launch

// Adicionar agrupamento na lista de encerramento de conta - Isso vai facilitar a divisão na hora do pagamento
// no caso de um casal, normalmente um só paga
// ---------
// Adicionar auto complete baseado nos itens já inseridos, trazendo uma sugestão de valor para o item
// ---------
// Adicionar MLKit para ler e interpretar cardapios, de forma a ajudar no autocomplete
// ---------
// Criar tabela para gerenciar grupos de pessoas - many to many
// Criar tela para criar grupos

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            val navController = rememberNavController()
            val canNavigateBack = remember { mutableStateOf(false) }
            val screenTitle = remember { mutableIntStateOf(Screen.Bills.title) }
            val isTopAppBarVisible = remember { mutableStateOf(true) }
            val snackbarHostState = remember { SnackbarHostState() }
            val snackbarType = remember { mutableStateOf(SnackbarType.SUCCESS) }
            val scope = rememberCoroutineScope()

            onDestinationHandle(
                navController = navController,
                updateNavigateBack = { canNavigateBack.value = it },
                updateScreenTitle = { screenTitle.intValue = it },
                updateTopAppBarVisible = { isTopAppBarVisible.value = it }
            )
            Box(modifier = Modifier.safeDrawingPadding()) {
                // TODO temporário
                BillBuddyTheme(darkTheme = false) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = {
                            CustomSnackbarHost(snackbarHostState, snackbarType.value)
                        },
                    ) { innerPadding ->
                        NavigationView(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            onShowSnackbar = {
                                snackbarType.value = it.type
                                scope.launch {
                                    snackbarHostState.showSnackbar(it.message)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CustomSnackbarHost(
    snackbarHostState: SnackbarHostState,
    type: SnackbarType,
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(horizontal = PaddingTwo, vertical = PaddingThree),
        snackbar = {
            when (type) {
                SnackbarType.SUCCESS -> {

                }
                SnackbarType.WARNING -> {
                    WarningSnackbar(message = it.visuals.message)
                }
                SnackbarType.ERROR -> {

                }
            }
        }
    )
}

private fun onDestinationHandle(
    navController: NavHostController,
    updateNavigateBack: (Boolean) -> Unit,
    updateScreenTitle: (Int) -> Unit,
    updateTopAppBarVisible: (Boolean) -> Unit,
) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.route) {
            Screen.Bills.route -> {
                updateNavigateBack(false)
                updateTopAppBarVisible(false)
                updateScreenTitle(Screen.Bills.title)
            }

            Screen.Bill.route -> {
                updateNavigateBack(true)
                updateTopAppBarVisible(true)
                updateScreenTitle(Screen.Bill.title)
            }

            Screen.BillDetails.route -> {
                updateNavigateBack(false)
                updateTopAppBarVisible(false)
                updateScreenTitle(Screen.BillDetails.title)
            }

            Screen.CreateItem.route -> {
                updateNavigateBack(true)
                updateTopAppBarVisible(true)
                updateScreenTitle(Screen.CreateItem.title)
            }

            Screen.CloseBill.route -> {
                updateNavigateBack(true)
                updateTopAppBarVisible(false)
                updateScreenTitle(Screen.CloseBill.title)
            }

            Screen.Groups.route -> {
                updateNavigateBack(true)
                updateTopAppBarVisible(false)
                updateScreenTitle(Screen.Groups.title)
            }

            else -> throw Exception("Something went wrong")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BillBuddyTheme {

    }
}