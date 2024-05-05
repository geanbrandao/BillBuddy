package com.geanbrandao.br.billbuddy.presentation.createitem

sealed class CreateItemNavigationIntent {
    data object NavigateBack : CreateItemNavigationIntent()
}