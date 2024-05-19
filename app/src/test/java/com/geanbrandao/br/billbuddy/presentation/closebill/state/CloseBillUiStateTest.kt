package com.geanbrandao.br.billbuddy.presentation.closebill.state

import com.geanbrandao.br.billbuddy.domain.model.SpentByPersonModel
import junit.framework.TestCase.assertEquals
import org.junit.Test


class CloseBillUiStateTest {

    private val uiState = CloseBillUiState(
        billId = 1,
        serviceTaxPercentage = "10,00",
        spentByPerson = listOf(
            SpentByPersonModel(
                billId = 1,
                personId = 1,
                name = "João",
                totalSpent = 100f,
            ),
            SpentByPersonModel(
                billId = 1,
                personId = 2,
                name = "John",
                totalSpent = 200f,
            ),
        )
    )

    @Test
    fun `given float value then get formatted value`() {
        val expected = 0.1f

        assertEquals(expected, uiState.serviceTaxPercentageNumber)
    }

    @Test
    fun `given uiState then get total spent service tax formatted`() {
        val expected = "R$ 330,00"

        assertEquals(expected, uiState.totalSpentServiceTaxFormatted)
    }
}