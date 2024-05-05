package com.geanbrandao.br.billbuddy.presentation.createitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geanbrandao.br.billbuddy.presentation.navigation.AppNavigator
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CreateItemViewModel(
    private val appNavigator: AppNavigator,
) : ViewModel() {

    fun handleNavigation(intent: CreateItemNavigationIntent) {
        viewModelScope.launch {
            when (intent) {
                CreateItemNavigationIntent.NavigateBack -> {
                    appNavigator.navigateBack()
                }
            }
        }
    }
}