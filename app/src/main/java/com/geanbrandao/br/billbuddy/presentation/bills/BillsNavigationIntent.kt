package com.geanbrandao.br.billbuddy.presentation.bills

import com.geanbrandao.br.billbuddy.presentation.navigation.Screen

sealed class BillsNavigationIntent(val route: String) {
    data class NavigateToBill(val id: Int) : BillsNavigationIntent(Screen.Bill.route)
}