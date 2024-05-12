package com.geanbrandao.br.billbuddy.presentation.createitem

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geanbrandao.br.billbuddy.domain.usecase.UseCases
import com.geanbrandao.br.billbuddy.presentation.navigation.AppNavigator
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_UI_STATE = "keyCreateItemUiState"

@KoinViewModel
class CreateItemViewModel(
    private val appNavigator: AppNavigator,
    private val state: SavedStateHandle,
    private val useCases: UseCases,
) : ViewModel() {

    private val billId: String = checkNotNull(state["billId"])
    val uiState = state.getStateFlow(key = KEY_UI_STATE, CreateItemUiState())

    fun getCreateItem() {
        state[KEY_UI_STATE] = uiState.value.copy(billId = billId.toLong())
        getPersons()
    }

    private fun getPersons() {
        viewModelScope.launch {
            useCases.getPersonsUseCase(billId = billId.toLong())
                .catch {
                    // todo lidar com possíveis erros
                }.collect {
                    state[KEY_UI_STATE] = uiState.value.copy(persons = it)
                }
        }
    }

    fun handleNavigation(intent: CreateItemNavigationIntent) {
        viewModelScope.launch {
            when (intent) {
                CreateItemNavigationIntent.NavigateBack -> {
                    appNavigator.navigateBack()
                }
            }
        }
    }

    fun handleIntents(intent: CreateItemIntent) {
        viewModelScope.launch {
            when (intent) {
                CreateItemIntent.OnCreateItem -> {
                    saveItem()
                }

                CreateItemIntent.OnDivideByAll -> {
                    onDivideByAll()
                }

                is CreateItemIntent.OnNameChange -> {
                    state[KEY_UI_STATE] = uiState.value.copy(name = intent.value)
                }

                is CreateItemIntent.OnPersonChecked -> {
                    updatePersonsList(intent.isChecked, intent.id)
                }

                is CreateItemIntent.OnValueChange -> {
                    updateValue(intent.value)
                }
            }
        }
    }

    private fun saveItem() {
        viewModelScope.launch {
            useCases.createItemUseCase(uiState.value.resume)
                .catch {
                    // todo lidar com possíveis erros
                }.collect {
                    appNavigator.navigateBack()
                }
        }
    }

    private fun updatePersonsList(isChecked: Boolean, id: Long) {
        viewModelScope.launch {
            val persons = uiState.value.persons
            val updatedPersons = persons.map { person ->
                if (person.id == id) {
                    person.copy(isChecked = isChecked)
                } else {
                    person
                }
            }
            state[KEY_UI_STATE] = uiState.value.copy(persons = updatedPersons)
        }
    }

    private fun onDivideByAll() {
        viewModelScope.launch {
            val isChecked = uiState.value.persons.all { it.isChecked }.not()
            val persons = uiState.value.persons.map { person -> person.copy(isChecked = isChecked) }
            state[KEY_UI_STATE] = uiState.value.copy(persons = persons)
        }
    }

    private fun updateValue(text: String) {
        viewModelScope.launch {
            useCases.formatStringValueAsMoneyUseCase(text)
                .catch {
                    // todo lidar com possíveis erros
                }.collect {
                    state[KEY_UI_STATE] = uiState.value.copy(value = it)
                }
        }
    }
}