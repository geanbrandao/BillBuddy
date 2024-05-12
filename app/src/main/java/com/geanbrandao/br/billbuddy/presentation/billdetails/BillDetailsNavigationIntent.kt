package com.geanbrandao.br.billbuddy.presentation.billdetails

import com.geanbrandao.br.billbuddy.presentation.navigation.Screen

sealed class BillDetailsNavigationIntent(
    val route: String,
) {

    data object NavigateBack : BillDetailsNavigationIntent("")
    data class NavigateToCloseBill(val billId: Long) :
        BillDetailsNavigationIntent(Screen.CloseBill.route)

    data class NavigateToCreateItem(val billId: Long) :
        BillDetailsNavigationIntent(Screen.CreateItem.route.replace("{billId}", billId.toString()))
}