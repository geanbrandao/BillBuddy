package com.geanbrandao.br.billbuddy.presentation.billdetails

import com.geanbrandao.br.billbuddy.presentation.navigation.Screen

sealed class BillDetailsNavigationIntent(
    val route: String,
) {

    data object NavigateBack : BillDetailsNavigationIntent("")
    data class NavigateToCloseBill(val billId: Int) :
        BillDetailsNavigationIntent(Screen.CloseBill.route)

    data class NavigateToCreateItem(val billId: Int) :
        BillDetailsNavigationIntent(Screen.CreateItem.route)
}