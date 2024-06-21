package com.geanbrandao.br.billbuddy.presentation.bill.intents

import com.geanbrandao.br.billbuddy.navigation.Screen

sealed class BillNavigationIntent(val route: String) {
    data object NavigateBack : BillNavigationIntent(route = "") // todo ver a questão do null ou rota vazia
    data class NavigateToBillDetails(val billId: Int) : BillNavigationIntent(
        route = Screen.BillDetails.route.replace("{billId}", billId.toString()) // todo adicionar funcao build arguments
    )

    data class ShowWarningSnackbar(val message: String) : BillNavigationIntent("")
}