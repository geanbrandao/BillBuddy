package com.geanbrandao.br.billbuddy.domain.usecase

import com.geanbrandao.br.billbuddy.data.local.entity.BillEntity
import com.geanbrandao.br.billbuddy.data.local.entity.UserEntity
import com.geanbrandao.br.billbuddy.domain.model.BillStatus
import com.geanbrandao.br.billbuddy.domain.model.CreateBillModel
import com.geanbrandao.br.billbuddy.domain.repository.LocalRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class CreateBillUseCase(
    private val repository: LocalRepository,
) {
    operator fun invoke(bill: CreateBillModel) = flow {
        val id = repository.createBill(
            BillEntity(
                id = 0,
                name = bill.billName,
                status = BillStatus.NEW
            )
        )
        bill.persons.forEach {
            repository.createPerson(UserEntity(userId = 0, name = it, billId = id))
        }
        emit(id)
    }
}