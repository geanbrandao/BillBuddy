package com.geanbrandao.br.billbuddy.domain.usecase

import com.geanbrandao.br.billbuddy.utils.MoneyExtensions.MAX_MONETARY_LENGTH
import com.geanbrandao.br.billbuddy.utils.MoneyExtensions.formatValueAsMoney
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class FormatStringValueAsMoneyUseCase {

    operator fun invoke(text: String) = flow {
        val formattedText = formatValueAsMoney(text)
        val result = if (text.length <= MAX_MONETARY_LENGTH) {
            formattedText
        } else {
            text.substring(0, MAX_MONETARY_LENGTH)
        }
        emit(result)
    }
}