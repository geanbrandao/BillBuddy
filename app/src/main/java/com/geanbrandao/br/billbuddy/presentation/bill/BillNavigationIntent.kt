package com.geanbrandao.br.billbuddy.presentation.bill

import com.geanbrandao.br.billbuddy.presentation.navigation.Screen

sealed class BillNavigationIntent(val route: String) {
    data class NavigateToBillDetails(val id: Int) : BillNavigationIntent(Screen.BillDetails.route)
    // todo show warning message
}