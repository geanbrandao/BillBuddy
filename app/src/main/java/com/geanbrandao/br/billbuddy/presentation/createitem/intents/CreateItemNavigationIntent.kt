package com.geanbrandao.br.billbuddy.presentation.createitem.intents

sealed class CreateItemNavigationIntent {
    data object NavigateBack : CreateItemNavigationIntent()
}