package com.geanbrandao.br.billbuddy.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.geanbrandao.br.billbuddy.domain.model.SnackbarDataModel
import com.geanbrandao.br.billbuddy.presentation.bill.BillScreen
import com.geanbrandao.br.billbuddy.presentation.billdetails.BillDetailsScreen
import com.geanbrandao.br.billbuddy.presentation.bills.BillsScreen
import com.geanbrandao.br.billbuddy.presentation.closebill.CloseBillScreen
import com.geanbrandao.br.billbuddy.presentation.createitem.CreateItemScreen
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationView(
    navController: NavHostController,
    onShowSnackbar: (SnackbarDataModel) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NavigationViewModel = koinViewModel(),
) {

    val activity = (LocalContext.current as? Activity)
    val navigationChannel = viewModel.navigationChannel

    LaunchedEffect(activity, navController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing.orFalse()) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route.isNotNull()) {
                        navController.popBackStack(intent.route!!, intent.inclusive)
                    } else {
                        navController.popBackStack()
                    }
                }
                is NavigationIntent.NavigateTo -> {
                    navController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }

                is NavigationIntent.ShowSnackbar -> {
                    onShowSnackbar(
                        SnackbarDataModel(
                            message = intent.message,
                            type = intent.type,
                            actionLabel = intent.actionLabel
                        )
                    )
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = Screen.Bills.route, modifier = modifier) {
        composable(Screen.Bills.route) {
            BillsScreen()
        }
        composable(Screen.Bill.route) {
            BillScreen()
        }
        composable(Screen.BillDetails.route) {
            BillDetailsScreen()
        }
        composable(Screen.CreateItem.route) {
            CreateItemScreen()
        }
        composable(Screen.CloseBill.route) {
            CloseBillScreen()
        }
    }
}

fun Boolean?.orFalse() = this ?: false
fun Any?.isNotNull() = this != null