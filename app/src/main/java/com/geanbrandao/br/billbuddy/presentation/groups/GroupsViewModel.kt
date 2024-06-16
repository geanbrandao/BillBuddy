package com.geanbrandao.br.billbuddy.presentation.groups

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.geanbrandao.br.billbuddy.presentation.groups.state.GroupsUiState
import org.koin.android.annotation.KoinViewModel

private const val KEY_UI_STATE = "keyGroupsUiState"

@KoinViewModel
class GroupsViewModel(
    private val state: SavedStateHandle,
): ViewModel() {

    val uiState = state.getStateFlow(key = KEY_UI_STATE, initialValue = GroupsUiState())
}