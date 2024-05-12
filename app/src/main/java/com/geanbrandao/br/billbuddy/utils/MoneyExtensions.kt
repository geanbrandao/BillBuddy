package com.geanbrandao.br.billbuddy.utils

object MoneyExtensions {
    const val MAX_MONETARY_LENGTH = 10
    const val ZERO_MONEY = "0,00"

    fun formatValueAsMoney(value: String) = "%.2f".format(value.getDigits().toDouble() / 100f)

    private fun String.getDigits() = this.filter { it.isDigit() }

    fun String.moneyBrlToFloat() = this.getDigits().toFloat() / 100f
}
