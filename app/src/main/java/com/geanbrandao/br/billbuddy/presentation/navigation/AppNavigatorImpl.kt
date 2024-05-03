package com.geanbrandao.br.billbuddy.presentation.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import org.koin.core.annotation.Single

@Single
class AppNavigatorImpl: AppNavigator {
    override val navigationChannel: Channel<NavigationIntent>
        get() = Channel(
            capacity = Int.MAX_VALUE,
            onBufferOverflow = BufferOverflow.DROP_LATEST
        )

    override suspend fun navigateBack(route: String?, isInclusive: Boolean) {
        navigationChannel.send(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = isInclusive,
            )
        )
    }

    override suspend fun navigateTo(
        route: String,
        popUpToRoute: String?,
        inclusive: Boolean,
        isSingleTop: Boolean
    ) {
        navigationChannel.send(
            NavigationIntent.NavigateTo(
                route = route,
                popUpToRoute = popUpToRoute,
                inclusive = inclusive,
                isSingleTop = isSingleTop,
            )
        )
    }
}