package com.geanbrandao.br.billbuddy.domain.usecase

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class FormatStringValueAsPercentageServiceTaxUseCaseTest {

    @Test
    fun `given string with number bigger than max then return max percentage`() = runTest {
        val useCase = FormatStringValueAsPercentageServiceTaxUseCase()
        val result = useCase.invoke("150").first()
        assertEquals("100", result)
    }

    @Test
    fun `given string with correct number then return percentage`() = runTest {
        val useCase = FormatStringValueAsPercentageServiceTaxUseCase()
        val result = useCase.invoke("85").first()
        assertEquals("85", result)
    }

    @Test
    fun `given string with length bigger than max then return max percentage`() = runTest {
        val useCase = FormatStringValueAsPercentageServiceTaxUseCase()
        val result = useCase.invoke("1234").first()
        assertEquals("100", result)
    }

    @Test
    fun `given string with invalid characters return zero`() = runTest {
        val useCase = FormatStringValueAsPercentageServiceTaxUseCase()
        val result = useCase.invoke("abc").first()
        assertEquals("0", result)
    }

    @Test
    fun `given string beginning with zero then return correct percentage`() = runTest {
        val useCase = FormatStringValueAsPercentageServiceTaxUseCase()
        val result = useCase.invoke("01").first()
        assertEquals("1", result)
    }
}