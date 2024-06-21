package com.geanbrandao.br.billbuddy.presentation.bill

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geanbrandao.br.billbuddy.domain.model.CreateBillModel
import com.geanbrandao.br.billbuddy.domain.usecase.UseCases
import com.geanbrandao.br.billbuddy.navigation.AppNavigator
import com.geanbrandao.br.billbuddy.navigation.Screen
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillIntent
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillNavigationIntent
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillNavigationIntent.NavigateToBillDetails
import com.geanbrandao.br.billbuddy.presentation.bill.intents.BillNavigationIntent.ShowWarningSnackbar
import com.geanbrandao.br.billbuddy.presentation.bill.state.BillUiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_UI_STATE = "keyBillUiState"

@KoinViewModel
class BillViewModel(
    private val appNavigator: AppNavigator,
    private val state: SavedStateHandle,
    private val useCases: UseCases,
) : ViewModel() {

    val uiState = state.getStateFlow(key = KEY_UI_STATE, initialValue = BillUiState())

    fun handleNavigation(intent: BillNavigationIntent) {
        viewModelScope.launch {
            when (intent) {
                is NavigateToBillDetails -> {
                    appNavigator.navigateTo(
                        route = intent.route,
                        popUpToRoute = Screen.Bill.route,
                        inclusive = true,
                    )
                }

                is ShowWarningSnackbar -> {
                    appNavigator.showSnackbar(message = intent.message)
                }

                BillNavigationIntent.NavigateBack -> TODO()
            }
        }
    }

    fun handleIntents(intent: BillIntent) {
        viewModelScope.launch {
            when (intent) {
                is BillIntent.OnBillNameChange -> {
                    state[KEY_UI_STATE] = uiState.value.copy(billName = intent.value)
                }

                is BillIntent.OnPersonNameChange -> {
                    state[KEY_UI_STATE] = uiState.value.copy(personName = intent.value)
                }

                is BillIntent.OnAddNewPerson -> {
                    addPersonToList(intent.value)
                }

                is BillIntent.OnRemovePerson -> {
                    removePersonFromList(intent.value)
                }

                BillIntent.OnCreateBill -> { createBill() }
            }
        }
    }

    private fun removePersonFromList(value: String) {
        val persons = uiState.value.persons
        state[KEY_UI_STATE] = uiState.value.copy(persons = persons.minus(value))
    }

    private fun addPersonToList(value: String) {
        val persons = uiState.value.persons
        when {
            value.isEmpty() -> {
                handleNavigation(ShowWarningSnackbar(message = "O nome não pode ser vazio."))
            }
            persons.contains(value) -> {
                handleNavigation(ShowWarningSnackbar(message = "O nome '$value' já existe."))
            }
            else -> {
                state[KEY_UI_STATE] = uiState.value.copy(persons = persons.plus(value), personName = "")
            }
        }
    }

    private fun createBill() {
        viewModelScope.launch {
            useCases.createBillUseCase(getData())
                .catch {
                    // todo lidar com possíveis erros
                }.collect { billId: Long ->
                    handleNavigation(intent = NavigateToBillDetails(billId = billId.toInt()))
                }

        }
    }

    private fun getData() = CreateBillModel(
        billName = uiState.value.billName,
        persons = uiState.value.persons,
    )
}