package com.geanbrandao.br.billbuddy.presentation.billdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geanbrandao.br.billbuddy.presentation.billdetails.BillDetailsNavigationIntent.NavigateBack
import com.geanbrandao.br.billbuddy.presentation.billdetails.BillDetailsNavigationIntent.NavigateToCloseBill
import com.geanbrandao.br.billbuddy.presentation.billdetails.BillDetailsNavigationIntent.NavigateToCreateItem
import com.geanbrandao.br.billbuddy.presentation.navigation.AppNavigator
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class BillDetailsViewModel(
    private val appNavigator: AppNavigator,
) : ViewModel() {

    fun handleNavigation(intent: BillDetailsNavigationIntent) {
        viewModelScope.launch {
            when (intent) {
                NavigateBack -> {
                    appNavigator.navigateBack()
                }
                is NavigateToCloseBill -> {
                    appNavigator.navigateTo(route = intent.route)
                }
                is NavigateToCreateItem -> {
                    appNavigator.navigateTo(route = intent.route)
                }
            }
        }
    }

}