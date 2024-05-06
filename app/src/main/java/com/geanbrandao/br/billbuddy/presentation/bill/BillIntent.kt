package com.geanbrandao.br.billbuddy.presentation.bill

sealed class BillIntent {
    data class OnBillNameChange(val value: String) : BillIntent()
    data class OnPersonNameChange(val value: String) : BillIntent()
    data class OnAddNewPerson(val value: String) : BillIntent()
    data class OnRemovePerson(val value: String) : BillIntent()
}