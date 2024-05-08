package com.geanbrandao.br.billbuddy.domain.usecase

import com.geanbrandao.br.billbuddy.domain.model.BillModel
import com.geanbrandao.br.billbuddy.domain.repository.LocalRepository
import com.geanbrandao.br.billbuddy.utils.formatToBrl
import com.geanbrandao.br.billbuddy.utils.getBrStatus
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetBillsUseCase(
    private val repository: LocalRepository,
) {

    operator fun invoke() = flow {
        val bills = repository.getBills()
        bills.map { bill ->
            val items = repository.getItems(bill.id)
            val total = items.sumOf { it.value.toDouble() }.toFloat()
            val totalFormatted = total.formatToBrl()
            BillModel(
                id = bill.id,
                name = bill.name,
                status = bill.status.getBrStatus(),
                total = totalFormatted,
            )
        }.apply {
            emit(this)
        }

    }
}