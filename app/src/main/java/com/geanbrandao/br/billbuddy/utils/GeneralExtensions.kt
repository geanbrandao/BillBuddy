package com.geanbrandao.br.billbuddy.utils

import com.geanbrandao.br.billbuddy.domain.model.BillStatus
import java.util.Locale

fun Float.formatToBrl(): String = String.format("R$ %.2f", this, Locale("pt", "BR"))

fun BillStatus.getBrStatus(): String = when(this) {
    BillStatus.OPEN -> "Em aberto"
    BillStatus.CLOSED -> "Fechada"
    BillStatus.NEW -> "Nova"
}