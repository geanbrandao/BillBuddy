package com.geanbrandao.br.billbuddy.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.geanbrandao.br.billbuddy.utils.formatToBrl
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class DividedValueModel(
    val userId: Long,
    val userName: String,
    val value: Float,
) : Parcelable {
    val formatedValue: String
        get() = value.formatToBrl()
}
