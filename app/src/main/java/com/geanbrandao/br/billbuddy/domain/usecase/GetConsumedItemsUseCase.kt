package com.geanbrandao.br.billbuddy.domain.usecase

import com.geanbrandao.br.billbuddy.data.local.entity.UserWithItemDividedValue
import com.geanbrandao.br.billbuddy.domain.model.ConsumedItemModel
import com.geanbrandao.br.billbuddy.domain.model.DividedValueModel
import com.geanbrandao.br.billbuddy.domain.repository.LocalRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetConsumedItemsUseCase(
    private val repository: LocalRepository,
) {

    operator fun invoke(billId: Long) = flow {
        val items = repository.getItemsDividedValue(billId)

        items.groupBy { it.itemId }.map { item: Map.Entry<Long, List<UserWithItemDividedValue>> ->
            ConsumedItemModel(
                itemId = item.key,
                name = item.value.first().itemName,
                value = item.value.sumOf { it.value.toDouble() }.toFloat(),
                dividedValues = item.value.map {
                    DividedValueModel(
                        userId = it.userId,
                        userName = it.userName,
                        value = it.value,
                    )
                }
            )
        }.sortedByDescending {
            it.itemId
        }.apply {
            emit(this)
        }
    }
}