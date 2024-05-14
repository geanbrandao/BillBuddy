package com.geanbrandao.br.billbuddy.navigation

import androidx.compose.material3.SnackbarDuration
import com.geanbrandao.br.billbuddy.domain.model.SnackbarType

sealed class NavigationIntent {
    data class NavigateBack(
        val route: String? = null,
        val inclusive: Boolean = false,
    ) : NavigationIntent()

    data class NavigateTo(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
    ) : NavigationIntent()

    data class ShowSnackbar(
        val message: String,
        val type: SnackbarType = SnackbarType.WARNING,
        val actionLabel: String? = null,
        val duration: SnackbarDuration = SnackbarDuration.Short,
    ) : NavigationIntent()
}