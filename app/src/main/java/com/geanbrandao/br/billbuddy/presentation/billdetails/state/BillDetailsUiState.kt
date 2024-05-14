package com.geanbrandao.br.billbuddy.presentation.billdetails.state

import android.os.Parcelable
import com.geanbrandao.br.billbuddy.domain.model.ConsumedItemModel
import com.geanbrandao.br.billbuddy.domain.model.SpentByPersonModel
import com.geanbrandao.br.billbuddy.utils.formatToBrl
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillDetailsUiState(
    val billId: Long = -1,
    val billName: String = "",
    val totalValue: Float = 0.0f,
    val items: List<ConsumedItemModel> = listOf(),
    val spentByPerson: List<SpentByPersonModel> = listOf(),
    val isConfirmationDialogOpen: Boolean = false,
    val idItemToRemove: Long? = null,
) : Parcelable {
    val totalValueFormatted: String
        get() = totalValue.formatToBrl()
}

