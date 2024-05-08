package com.geanbrandao.br.billbuddy.domain.model

enum class BillStatus {
    OPEN, CLOSED, NEW;

    companion object {
        fun from(value: String) = entries.find { it.name == value }
            ?: throw Exception("Wrong bill status")
    }
}