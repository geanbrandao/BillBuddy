package com.geanbrandao.br.billbuddy.navigation

import kotlinx.coroutines.channels.Channel

interface AppNavigator {
    val navigationChannel: Channel<NavigationIntent>

    suspend fun navigateBack(
        route: String? = null,
        isInclusive: Boolean = false,
    )

    suspend fun navigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )
    // todo adicionar funcao para exibir erros
}