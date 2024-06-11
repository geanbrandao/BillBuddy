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

    fun totalSpentWithServiceTaxFormatted(serviceTax: Float): String =
        (totalSpent + (totalSpent.times(serviceTax))).formatToBrl()

    fun getTotalSpentDetails(serviceTax: Float): String = buildString {
        append(totalSpentFormatted)
//        if (serviceTax > 0) {
            append(" + ")
            val personSpentServiceFee = totalSpent * serviceTax
            val personSpentServiceFeeFormatted = personSpentServiceFee.formatToBrl()
            append(personSpentServiceFeeFormatted)
//        }
    }
}

