package com.geanbrandao.br.billbuddy.presentation.closebill.intents

sealed class CloseBillNavigationIntent(val route: String?) {
    data object NavigateBack : CloseBillNavigationIntent(route = null)
}