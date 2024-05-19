package com.geanbrandao.br.billbuddy.domain.usecase

import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class FormatStringValueAsPercentageServiceTaxUseCase {

    operator fun invoke(text: String) = flow {
        emit(text.toPercentageFormat())
    }

    fun String.toPercentageFormat(): String {
        // Remove todos os caracteres que não são números, ponto ou vírgula
        val cleanedString = this.replace(Regex("[^\\d.,]"), "")

        // Substitui pontos por vírgulas
        val standardizedString = cleanedString.replace(".", ",")

        // Se não há vírgula, adicione ",0" ao final
        val formattedString = if (standardizedString.contains(",")) {
            standardizedString
        } else {
            "$standardizedString,0"
        }

        // Divide a string em parte inteira e decimal
        val parts = formattedString.split(",")
        val integerPart = if (parts[0].isEmpty()) "0" else parts[0]
        val decimalPart = parts.getOrNull(1)?.padEnd(2, '0') ?: "00"

        // Trunca ou preenche a parte decimal para garantir duas casas decimais
        val truncatedDecimalPart = decimalPart.take(2)

        return "$integerPart,$truncatedDecimalPart"
    }
}