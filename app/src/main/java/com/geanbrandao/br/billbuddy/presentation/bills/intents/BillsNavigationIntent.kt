package com.geanbrandao.br.billbuddy.presentation.bills.intents

import com.geanbrandao.br.billbuddy.navigation.Screen

sealed class BillsNavigationIntent(val route: String) {
    data class NavigateToBill(val id: Long) : BillsNavigationIntent(Screen.Bill.route)
    data class NavigateToBillDetails(val id: Long) :
        BillsNavigationIntent(Screen.BillDetails.route.replace("{billId}", id.toString()))
}