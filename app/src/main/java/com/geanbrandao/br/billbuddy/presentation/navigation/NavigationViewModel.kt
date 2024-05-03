package com.geanbrandao.br.billbuddy.presentation.navigation

import androidx.lifecycle.ViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NavigationViewModel(
    private val appNavigator: AppNavigator,
): ViewModel() {
    val navigationChannel = appNavigator.navigationChannel
}