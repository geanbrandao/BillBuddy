package com.geanbrandao.br.billbuddy.presentation.closebill.intents

sealed class CloseBillIntent {
    data class OnServiceTaxPercentageChange(val value: String) : CloseBillIntent()
}