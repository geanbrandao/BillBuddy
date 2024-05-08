package com.geanbrandao.br.billbuddy.domain.usecase

import com.geanbrandao.br.billbuddy.data.local.entity.BillEntity
import com.geanbrandao.br.billbuddy.domain.model.BillModel
import com.geanbrandao.br.billbuddy.domain.model.BillStatus
import com.geanbrandao.br.billbuddy.domain.repository.LocalRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class CreateBillUseCase(
    private val repository: LocalRepository,
) {
    operator fun invoke(bill: BillModel) = flow {
        val id = repository.createBill(
            BillEntity(
                id = 0,
                name = bill.name,
                status = BillStatus.NEW
            )
        )
        emit(id)
    }
}