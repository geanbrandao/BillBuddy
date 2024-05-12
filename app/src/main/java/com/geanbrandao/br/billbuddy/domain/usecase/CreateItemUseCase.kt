package com.geanbrandao.br.billbuddy.domain.usecase

import com.geanbrandao.br.billbuddy.data.local.entity.ItemEntity
import com.geanbrandao.br.billbuddy.data.local.entity.UserItemCrossRef
import com.geanbrandao.br.billbuddy.domain.model.CreateItemModel
import com.geanbrandao.br.billbuddy.domain.repository.LocalRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class CreateItemUseCase(
    private val repository: LocalRepository,
) {

    operator fun invoke(model: CreateItemModel) = flow {
        val itemId = repository.createItem(
            ItemEntity(
                itemId = 0,
                name = model.itemName,
                value = model.itemValue,
                billId = model.billId,
            )
        )
        model.dividedValues.forEach {
            repository.createItemCrossRef(
                UserItemCrossRef(
                    userId = it.personId,
                    itemId = itemId,
                    dividedValue = it.dividedValue,
                )
            )
        }
        emit(itemId)
    }
}

