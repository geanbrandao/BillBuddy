package com.geanbrandao.br.billbuddy.navigation

import androidx.compose.material3.SnackbarDuration
import com.geanbrandao.br.billbuddy.domain.model.SnackbarType
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import org.koin.core.annotation.Single

@Single
class AppNavigatorImpl : AppNavigator {
    override val navigationChannel = Channel<NavigationIntent>(
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

    override suspend fun showSnackbar(
        message: String,
        type: SnackbarType,
        actionLabel: String?,
        duration: SnackbarDuration
    ) {
        navigationChannel.send(
            NavigationIntent.ShowSnackbar(
                message = message,
                type = type,
                actionLabel = actionLabel,
                duration = duration,
            )
        )
    }
}