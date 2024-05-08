package com.geanbrandao.br.billbuddy.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillModel(
    val id: Long,
    val name: String,
    val status: String,
    val total: String,
): Parcelable
