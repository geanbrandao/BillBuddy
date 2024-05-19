package com.geanbrandao.br.billbuddy.presentation.billdetails.intents

import com.geanbrandao.br.billbuddy.navigation.Screen

sealed class BillDetailsNavigationIntent(
    val route: String,
) {

    data object NavigateBack : BillDetailsNavigationIntent("")
    data class NavigateToCloseBill(val billId: Long) :
        BillDetailsNavigationIntent(Screen.CloseBill.route.replace("{billId}", billId.toString()))

    data class NavigateToCreateItem(val billId: Long) :
        BillDetailsNavigationIntent(Screen.CreateItem.route.replace("{billId}", billId.toString()))
}