package com.geanbrandao.br.billbuddy.presentation.bills

import android.os.Parcelable
import com.geanbrandao.br.billbuddy.domain.model.BillModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillsUiState(
    val bills: List<BillModel> = emptyList(),
): Parcelable