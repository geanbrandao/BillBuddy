package com.geanbrandao.br.billbuddy.domain.usecase

import com.geanbrandao.br.billbuddy.domain.repository.LocalRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetBillNameUseCase(
    private val repository: LocalRepository,
) {

    operator fun invoke(billId: Long) = flow {
        val billName = repository.getBill(billId = billId)
        emit(billName.name)
    }
}