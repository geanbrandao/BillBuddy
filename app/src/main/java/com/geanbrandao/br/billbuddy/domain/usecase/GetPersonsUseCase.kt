package com.geanbrandao.br.billbuddy.domain.usecase

import com.geanbrandao.br.billbuddy.domain.model.PersonModel
import com.geanbrandao.br.billbuddy.domain.repository.LocalRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetPersonsUseCase(
    private val repository: LocalRepository,
) {

    operator fun invoke(billId: Long) = flow {
        val persons = repository.getPersons(billId = billId)
        persons.map {
            PersonModel(id = it.userId, name = it.name, isChecked = false)
        }.sortedBy {
            it.id
        }.apply { emit(this) }
    }
}