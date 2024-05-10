package com.geanbrandao.br.billbuddy.domain.usecase

import com.geanbrandao.br.billbuddy.data.local.entity.UserWithItemDividedValue
import com.geanbrandao.br.billbuddy.domain.model.SpentByPersonModel
import com.geanbrandao.br.billbuddy.domain.repository.LocalRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetPersonsSpentUseCase(
    private val repository: LocalRepository,
) {

    operator fun invoke(billId: Long) = flow {
        val items = repository.getItemsDividedValue(billId)

        items.groupBy { it.userId }.map { item: Map.Entry<Long, List<UserWithItemDividedValue>> ->
            SpentByPersonModel(
                billId = billId,
                personId = item.key,
                name = item.value.first().userName,
                totalSpent = item.value.sumOf { it.value.toDouble() }.toFloat(),
            )
        }.sortedBy {
            it.personId
        }.apply {
            emit(this)
        }
    }
}