package com.geanbrandao.br.billbuddy.presentation.bills

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geanbrandao.br.billbuddy.data.local.dao.AppDao
import com.geanbrandao.br.billbuddy.domain.usecase.UseCases
import com.geanbrandao.br.billbuddy.navigation.AppNavigator
import com.geanbrandao.br.billbuddy.presentation.bills.intents.BillsIntent
import com.geanbrandao.br.billbuddy.presentation.bills.intents.BillsNavigationIntent
import com.geanbrandao.br.billbuddy.presentation.bills.state.BillsUiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_UI_STATE = "keyBillsUiState"

@KoinViewModel
class BillsViewModel(
    private val appNavigator: AppNavigator,
    private val state: SavedStateHandle,
    private val useCases: UseCases,
    private val appDao: AppDao,
) : ViewModel() {

    val uiState = state.getStateFlow(key = KEY_UI_STATE, BillsUiState())

    fun getBills() {
        viewModelScope.launch {
            useCases.getBillsUseCase().collect {
                state[KEY_UI_STATE] = uiState.value.copy(bills = it)
            }
        }
    }

    fun handleNavigation(intent: BillsNavigationIntent) {
        viewModelScope.launch {
            when (intent) {
                is BillsNavigationIntent.NavigateToBill -> {
                    appNavigator.navigateTo(route = intent.route)
                }

                is BillsNavigationIntent.NavigateToBillDetails -> {
                    appNavigator.navigateTo(route = intent.route)
                }
            }
        }
    }

    fun handleIntent(intent: BillsIntent) {
        when (intent) {
            is BillsIntent.OnConfirmationDialogRemoveBill -> {
                state[KEY_UI_STATE] = uiState.value.copy(
                    isConfirmationDialogOpen = intent.isOpen,
                    idBillToRemove = intent.billId,
                )
            }

            is BillsIntent.OnConfirmationDialogRemoveBillPositiveButtonClicked -> {
                removeBill(intent.billId)
            }
        }
    }

    private fun removeBill(billId: Long?) {
        viewModelScope.launch {
            useCases.removeBillUseCase(billId = billId)
                .catch {  }
                .collect {
                    getBills()
                }
        }
    }
}