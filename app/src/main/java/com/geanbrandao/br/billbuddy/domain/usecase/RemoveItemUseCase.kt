package com.geanbrandao.br.billbuddy.domain.usecase

import com.geanbrandao.br.billbuddy.domain.repository.LocalRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class RemoveItemUseCase(
    private val repository: LocalRepository,
) {

    operator fun invoke(itemId: Long?) = flow {
        repository.removeItem(itemId)
        emit(Unit)
    }
}
