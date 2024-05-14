package com.geanbrandao.br.billbuddy.presentation.billdetails


sealed class BillDetailsIntent {
    data class OnConfirmationDialogRemoveItem(val isOpen: Boolean, val itemId: Long? = null) : BillDetailsIntent()
    data class OnConfirmationDialogRemoveItemPositiveButtonClicked(val itemId: Long?) : BillDetailsIntent()
}
