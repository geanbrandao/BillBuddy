package com.geanbrandao.br.billbuddy.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonModel(
    val id: Long,
    val name: String,
    val isChecked: Boolean,
) : Parcelable
