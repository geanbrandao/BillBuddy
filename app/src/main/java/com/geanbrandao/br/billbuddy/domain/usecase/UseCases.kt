package com.geanbrandao.br.billbuddy.domain.usecase

import org.koin.core.annotation.Single

@Single
data class UseCases(
    val createBillUseCase: CreateBillUseCase,
    val getBillsUseCase: GetBillsUseCase,
    val getConsumedItemsUseCase: GetConsumedItemsUseCase,
    val getTotalBillUseCase: GetTotalBillUseCase,
    val getPersonsSpentUseCase: GetPersonsSpentUseCase,
    val getBillNameUseCase: GetBillNameUseCase,
)
