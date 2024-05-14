package com.geanbrandao.br.billbuddy.presentation.bills.intents

sealed class BillsIntent {
    data class OnConfirmationDialogRemoveBill(val isOpen: Boolean, val billId: Long? = null) : BillsIntent()
    data class OnConfirmationDialogRemoveBillPositiveButtonClicked(val billId: Long?) : BillsIntent()
}