package com.geanbrandao.br.billbuddy.domain.usecase

import com.geanbrandao.br.billbuddy.domain.repository.LocalRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetTotalBillUseCase(
    private val repository: LocalRepository,
) {

    operator fun invoke(billId: Long) = flow {
        val total = repository.getItems(billId)
            .sumOf { it.value.toDouble() }
            .toFloat()
        emit(total)
    }

}