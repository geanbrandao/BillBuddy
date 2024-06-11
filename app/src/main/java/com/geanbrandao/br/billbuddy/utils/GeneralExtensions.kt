package com.geanbrandao.br.billbuddy.utils

import android.annotation.SuppressLint
import com.geanbrandao.br.billbuddy.domain.model.BillStatus
import java.util.Locale

@SuppressLint("DefaultLocale")
fun Float.formatToBrl(): String = String.format("R$ %.2f", this, Locale("pt", "BR"))

@SuppressLint("DefaultLocale")
fun Float.formatPercentage(): String = String.format("%.2f %", this, Locale("pt", "BR"))

fun BillStatus.getBrStatus(): String = when(this) {
    BillStatus.OPEN -> "Em aberto"
    BillStatus.CLOSED -> "Fechada"
    BillStatus.NEW -> "Nova"
}

fun String.getDigits() = this.filter { it.isDigit() }

fun String.getPercentageNumber() = (this.replace(",", ".").toFloat()) / 100f