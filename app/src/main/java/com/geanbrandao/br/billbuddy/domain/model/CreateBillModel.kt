package com.geanbrandao.br.billbuddy.domain.model

data class CreateBillModel(
    val billName: String,
    val persons: List<String>,
)
