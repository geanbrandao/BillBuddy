package com.geanbrandao.br.billbuddy.presentation.closebill.state

import android.os.Parcelable
import com.geanbrandao.br.billbuddy.domain.model.SpentByPersonModel
import com.geanbrandao.br.billbuddy.utils.formatToBrl
import com.geanbrandao.br.billbuddy.utils.getPercentageNumber
import kotlinx.parcelize.Parcelize

@Parcelize
data class CloseBillUiState(
    val billId: Long = -1,
    val billName: String = "",
    val serviceTaxPercentage: String = "0,00",
    val spentByPerson: List<SpentByPersonModel> = listOf(),
) : Parcelable {
    val serviceTaxPercentageNumber: Float
        get() = serviceTaxPercentage.getPercentageNumber() //todo arrumar

    private val totalSpentNumber: Float
        get() = spentByPerson.sumOf { it.totalSpent.toDouble() }.toFloat()

    val totalSpentServiceTaxFormatted: String
        get() = (totalSpentNumber + (totalSpentNumber.times(serviceTaxPercentageNumber))).formatToBrl()
}
