package com.geanbrandao.br.billbuddy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.geanbrandao.br.billbuddy.presentation.bill.BillScreen
import com.geanbrandao.br.billbuddy.presentation.billdetails.BillDetailsScreen
import com.geanbrandao.br.billbuddy.presentation.bills.BillsScreen
import com.geanbrandao.br.billbuddy.presentation.closebill.CloseBillScreen
import com.geanbrandao.br.billbuddy.presentation.createitem.CreateItemScreen

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