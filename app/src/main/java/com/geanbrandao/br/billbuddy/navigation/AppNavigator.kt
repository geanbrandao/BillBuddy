package com.geanbrandao.br.billbuddy.navigation

import androidx.compose.material3.SnackbarDuration
import com.geanbrandao.br.billbuddy.domain.model.SnackbarType
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

    suspend fun showSnackbar(
        message: String,
        type: SnackbarType = SnackbarType.WARNING,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short,
    )
}