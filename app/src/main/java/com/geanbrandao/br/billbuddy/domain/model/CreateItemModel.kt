package com.geanbrandao.br.billbuddy.domain.model

import com.geanbrandao.br.billbuddy.utils.formatToBrl

data class CreateItemModel(
    val itemName: String,
    val itemValue: Float,
    val billId: Long,
    val dividedValues: List<DividedValue>,
) {
    val itemValueFormatted: String
        get() = itemValue.formatToBrl()

    data class DividedValue(
        val personId: Long,
        val dividedValue: Float,
        val personName: String,
    ) {
        val dividedValueFormatted: String
            get() = dividedValue.formatToBrl()
    }
}
