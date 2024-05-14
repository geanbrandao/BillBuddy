package com.geanbrandao.br.billbuddy.presentation.billdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geanbrandao.br.billbuddy.domain.usecase.UseCases
import com.geanbrandao.br.billbuddy.navigation.AppNavigator
import com.geanbrandao.br.billbuddy.presentation.billdetails.intents.BillDetailsIntent
import com.geanbrandao.br.billbuddy.presentation.billdetails.intents.BillDetailsNavigationIntent
import com.geanbrandao.br.billbuddy.presentation.billdetails.intents.BillDetailsNavigationIntent.NavigateBack
import com.geanbrandao.br.billbuddy.presentation.billdetails.intents.BillDetailsNavigationIntent.NavigateToCloseBill
import com.geanbrandao.br.billbuddy.presentation.billdetails.intents.BillDetailsNavigationIntent.NavigateToCreateItem
import com.geanbrandao.br.billbuddy.presentation.billdetails.state.BillDetailsUiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_UI_STATE = "keyBillDetailsUiState"

@KoinViewModel
class BillDetailsViewModel(
    private val appNavigator: AppNavigator,
    private val state: SavedStateHandle,
    private val useCases: UseCases,
) : ViewModel() {

    private val billId: String = checkNotNull(state["billId"])

    val uiState = state.getStateFlow(key = KEY_UI_STATE, BillDetailsUiState())

    fun getBillDetails() {
        state[KEY_UI_STATE] = uiState.value.copy(billId = billId.toLong())
        getBillDetailsItems()
        getTotalBill()
        getPersonsSpent()
        getBillName()
    }

    private fun getBillDetailsItems() {
        viewModelScope.launch {
            useCases.getConsumedItemsUseCase(billId = billId.toLong())
                .catch {
                    // todo lidar com possíveis erros
                }.collect {
                    state[KEY_UI_STATE] = uiState.value.copy(items = it)
                }
        }
    }

    private fun getTotalBill() {
        viewModelScope.launch {
            useCases.getTotalBillUseCase(billId = billId.toLong())
                .catch {
                    // todo lidar com possíveis erros
                }.collect {
                    state[KEY_UI_STATE] = uiState.value.copy(totalValue = it)
                }
        }
    }

    private fun getBillName() {
        viewModelScope.launch {
            useCases.getBillNameUseCase(billId = billId.toLong())
                .catch {
                    // todo lidar com possíveis erros
                }.collect {
                    state[KEY_UI_STATE] = uiState.value.copy(billName = it)
                }
        }
    }

    private fun getPersonsSpent() {
        viewModelScope.launch {
            useCases.getPersonsSpentUseCase(billId = billId.toLong())
                .catch {
                    // todo lidar com possíveis erros
                }.collect {
                    state[KEY_UI_STATE] = uiState.value.copy(spentByPerson = it)
                }
        }
    }

    private fun removeItem(itemId: Long?) {
        viewModelScope.launch {
            useCases.removeItemUseCase(itemId = itemId)
                .catch {
                    // todo lidar com possíveis erros
                }.collect {
                    getBillDetails()
                }
        }
    }

    fun handleIntent(intent: BillDetailsIntent) {
        viewModelScope.launch {
            when (intent) {
                is BillDetailsIntent.OnConfirmationDialogRemoveItemPositiveButtonClicked -> {
                    removeItem(intent.itemId)
                }

                is BillDetailsIntent.OnConfirmationDialogRemoveItem -> {
                    state[KEY_UI_STATE] = uiState.value.copy(
                        isConfirmationDialogOpen = intent.isOpen,
                        idItemToRemove = intent.itemId
                    )
                }
            }
        }
    }

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
