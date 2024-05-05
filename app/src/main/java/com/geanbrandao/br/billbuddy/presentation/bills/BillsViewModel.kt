package com.geanbrandao.br.billbuddy.presentation.bills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geanbrandao.br.billbuddy.presentation.navigation.AppNavigator
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class BillsViewModel(
    private val appNavigator: AppNavigator,
) : ViewModel() {



    fun handleNavigation(intent: BillsNavigationIntent) {
        viewModelScope.launch {
            when (intent) {
                is BillsNavigationIntent.NavigateToBill -> {
                    appNavigator.navigateTo(route = intent.route)
                }
            }
        }
    }
}