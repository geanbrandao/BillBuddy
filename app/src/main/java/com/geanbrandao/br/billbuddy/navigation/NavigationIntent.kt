package com.geanbrandao.br.billbuddy.navigation

import androidx.compose.material3.SnackbarDuration

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
        val actionLabel: String? = null,
        val duration: SnackbarDuration = SnackbarDuration.Short,
    ) : NavigationIntent()
}