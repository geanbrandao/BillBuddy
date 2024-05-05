package com.geanbrandao.br.billbuddy.presentation.bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geanbrandao.br.billbuddy.presentation.navigation.AppNavigator
import com.geanbrandao.br.billbuddy.presentation.navigation.Screen
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class BillViewModel(
    private val appNavigator: AppNavigator,
) : ViewModel() {

    fun handleNavigation(intent: BillNavigationIntent) {
        viewModelScope.launch {
            when (intent) {
                is BillNavigationIntent.NavigateToBillDetails -> {
                    appNavigator.navigateTo(
                        route = intent.route,
                        popUpToRoute = Screen.Bill.route,
                        inclusive = true,
                    )
                }
            }
        }
    }
}