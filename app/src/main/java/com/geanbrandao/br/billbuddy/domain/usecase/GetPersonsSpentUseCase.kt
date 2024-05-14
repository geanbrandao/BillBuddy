package com.geanbrandao.br.billbuddy.domain.usecase

import com.geanbrandao.br.billbuddy.data.local.entity.UserEntity
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
        val persons = repository.getPersons(billId)
        val itemsGroupByPerson = items.groupBy { it.userId }

        persons.map { person: UserEntity ->
            val totalSpent = itemsGroupByPerson[person.userId]?.sumOf { it.value.toDouble() }?.toFloat() ?: 0f
            SpentByPersonModel(
                billId = billId,
                personId = person.userId,
                name = person.name,
                totalSpent = totalSpent,
            )
        }.sortedBy {
            it.personId
        }.apply {
            emit(this)
        }
    }
}
