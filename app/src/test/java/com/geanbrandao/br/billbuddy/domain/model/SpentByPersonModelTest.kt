package com.geanbrandao.br.billbuddy.domain.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class SpentByPersonModelTest {

    private val model = SpentByPersonModel(
        billId = 1,
        personId = 1,
        name = "João",
        totalSpent = 100.0f
    )

    @Test
    fun `given float value then get formatted value`() {
        val expected = "R$ 100,00"

        assertEquals(expected, model.totalSpentFormatted)
    }

    @Test
    fun `when totalWithServiceTax then return formatted value`() {
        val expected = "R$ 110,00"

        assertEquals(expected, model.totalSpentWithServiceTaxFormatted(0.1f))
    }
}