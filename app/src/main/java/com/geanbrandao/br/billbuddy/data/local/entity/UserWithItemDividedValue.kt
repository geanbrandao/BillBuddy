package com.geanbrandao.br.billbuddy.data.local.entity

data class UserWithItemDividedValue(
    val userId: Long,
    val itemId: Long,
    val userName: String,
    val itemName: String,
    val value: Float,
)
