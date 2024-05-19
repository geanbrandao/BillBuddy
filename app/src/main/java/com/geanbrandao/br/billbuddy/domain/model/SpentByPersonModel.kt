package com.geanbrandao.br.billbuddy.domain.model

import android.os.Parcelable
import com.geanbrandao.br.billbuddy.utils.formatToBrl
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpentByPersonModel(
    val billId: Long,
    val personId: Long,
    val name: String,
    val totalSpent: Float,
) : Parcelable {
    val totalSpentFormatted: String
        get() = totalSpent.formatToBrl()

    fun totalWithServiceTax(serviceTax: Float): String =
        (totalSpent + (totalSpent.times(serviceTax))).formatToBrl()
}

