package com.geanbrandao.br.billbuddy.presentation.bill.intents

import com.geanbrandao.br.billbuddy.navigation.Screen

sealed class BillNavigationIntent(val route: String) {
    data class NavigateToBillDetails(val id: Int) : BillNavigationIntent(Screen.BillDetails.route)
    // todo show warning message
}