package com.geanbrandao.br.billbuddy.domain.model

data class SnackbarDataModel(
    val message: String,
    val type: SnackbarType = SnackbarType.WARNING,
    val actionLabel: String? = null,
)
