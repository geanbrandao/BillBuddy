package com.geanbrandao.br.billbuddy.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.geanbrandao.br.billbuddy.presentation.bills.BillsScreen

@Composable
fun NavigationView(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screen.Bills.route, modifier = modifier) {
        composable(Screen.Bills.route) {
            BillsScreen()
        }
        composable(Screen.Bill.route) {
            Text(text = "Bill")
        }
        composable(Screen.BillDetails.route) {
            Text(text = "Racha Conta")
        }
        composable(Screen.CreateItem.route) {
            Text(text = "Criar Item")
        }
        composable(Screen.CloseBill.route) {
            Text(text = "Fechar Conta")
        }
    }
}