package com.geanbrandao.br.billbuddy.presentation.createitem

sealed class CreateItemIntent {
    data class OnNameChange(val value: String) : CreateItemIntent()
    data class OnValueChange(val value: String) : CreateItemIntent()
    data class OnPersonChecked(val isChecked: Boolean, val id: Long) : CreateItemIntent()
    data object OnDivideByAll : CreateItemIntent()
    data object OnCreateItem : CreateItemIntent()
}