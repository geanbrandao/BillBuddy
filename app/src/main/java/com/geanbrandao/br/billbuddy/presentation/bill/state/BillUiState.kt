package com.geanbrandao.br.billbuddy.presentation.bill.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillUiState(
    val billName: String = "",
    val personName: String = "",
    val persons: List<String> = listOf(),
) : Parcelable {
    val isEnabled: Boolean
        get() = isValidBillName() && isValidPersons()

    private fun isValidPerson(): Boolean = this.personName.trim().isNotEmpty()

    private fun isValidBillName(): Boolean = this.billName.trim().isNotEmpty()

    private fun isValidPersons(): Boolean = this.persons.size > 1
}
