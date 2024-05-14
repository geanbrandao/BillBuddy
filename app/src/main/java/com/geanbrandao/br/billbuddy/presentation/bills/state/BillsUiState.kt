package com.geanbrandao.br.billbuddy.presentation.bills.state

import android.os.Parcelable
import com.geanbrandao.br.billbuddy.domain.model.BillModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillsUiState(
    val bills: List<BillModel> = emptyList(),
    val isConfirmationDialogOpen: Boolean = false,
    val idBillToRemove: Long? = null,
): Parcelable