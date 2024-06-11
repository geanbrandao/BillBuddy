package com.geanbrandao.br.billbuddy.presentation.closebill.state

import android.net.Uri
import android.os.Parcelable
import com.geanbrandao.br.billbuddy.domain.model.SpentByPersonModel
import com.geanbrandao.br.billbuddy.utils.formatToBrl
import com.geanbrandao.br.billbuddy.utils.getPercentageNumber
import kotlinx.parcelize.Parcelize

@Parcelize
data class CloseBillUiState(
    val billId: Long = -1,
    val billName: String = "",
    val serviceFeeFormatted: String = "0,00",
    val spentByPersonList: List<SpentByPersonModel> = listOf(),
    val uri: Uri? = null,
) : Parcelable {
    val serviceFee: Float
        get() = serviceFeeFormatted.getPercentageNumber()

    private val totalSpentNumber: Float
        get() = spentByPersonList.sumOf { it.totalSpent.toDouble() }.toFloat()

    val totalServiceFeeFormatted: String
        get() = (totalSpentNumber * serviceFee).formatToBrl()

    val totalSpentServiceFeeFormatted: String
        get() = (totalSpentNumber + (totalSpentNumber.times(serviceFee))).formatToBrl()
}
