package com.geanbrandao.br.billbuddy.presentation.closebill

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geanbrandao.br.billbuddy.domain.usecase.UseCases
import com.geanbrandao.br.billbuddy.navigation.AppNavigator
import com.geanbrandao.br.billbuddy.presentation.closebill.intents.CloseBillIntent
import com.geanbrandao.br.billbuddy.presentation.closebill.intents.CloseBillIntent.OnServiceTaxPercentageChange
import com.geanbrandao.br.billbuddy.presentation.closebill.intents.CloseBillNavigationIntent
import com.geanbrandao.br.billbuddy.presentation.closebill.intents.CloseBillNavigationIntent.NavigateBack
import com.geanbrandao.br.billbuddy.presentation.closebill.state.CloseBillUiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_UI_STATE = "keyCloseBillUiState"

@KoinViewModel
class CloseBillViewModel(
    private val state: SavedStateHandle,
    private val useCases: UseCases,
    private val appNavigator: AppNavigator,
) : ViewModel() {

    private val billId: String = checkNotNull(state["billId"])

    val uiState = state.getStateFlow(KEY_UI_STATE, CloseBillUiState())

    fun getCloseBill() {
        getBillName()
        getPersonsSpent()
    }

    fun handleIntent(intent: CloseBillIntent) {
        viewModelScope.launch {
            when (intent) {
                is OnServiceTaxPercentageChange -> {
                    updateServiceTaxPercentage(intent.value)
                }

                is CloseBillIntent.OnShareBillResume -> {
                    saveResumeImage(intent.bitmap)
                }
            }
        }
    }

    fun handleNavigation(intent: CloseBillNavigationIntent) {
        viewModelScope.launch {
            when (intent) {
                NavigateBack -> appNavigator.navigateBack(intent.route)
            }
        }
    }

    private fun saveResumeImage(bitmap: Bitmap) {
        viewModelScope.launch {
            useCases.saveResumeImageOnDiskUseCase(
                bitmap = bitmap,
                billName = uiState.value.billName
            ).catch {
                // todo lidar com possíveis erros
            }.collect {
                state[KEY_UI_STATE] = uiState.value.copy(uri = it)
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
                    state[KEY_UI_STATE] = uiState.value.copy(spentByPersonList = it)
                }
        }
    }

    private fun updateServiceTaxPercentage(value: String) {
        viewModelScope.launch {
            useCases.formatStringValueAsMoneyUseCase(text = value)
                .catch {
                    // todo lidar com possíveis erros
                }.collect { formattedValue: String ->
                    state[KEY_UI_STATE] = uiState.value.copy(serviceFeeFormatted = formattedValue)
                }
        }
    }

}