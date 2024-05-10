package com.geanbrandao.br.billbuddy.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.geanbrandao.br.billbuddy.utils.formatToBrl
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class ConsumedItemModel(
    val itemId: Long,
    val name: String,
    val value: Float,
    val dividedValues: List<DividedValueModel>,
) : Parcelable {
    val formatedValue: String
        get() = value.formatToBrl()
}