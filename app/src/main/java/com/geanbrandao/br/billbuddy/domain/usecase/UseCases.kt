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
    val getPersonsUseCase: GetPersonsUseCase,
    val formatStringValueAsMoneyUseCase: FormatStringValueAsMoneyUseCase,
    val createItemUseCase: CreateItemUseCase,
    val removeItemUseCase: RemoveItemUseCase,
    val removeBillUseCase: RemoveBillUseCase,
    val formatStringValueAsPercentageServiceTaxUseCase: FormatStringValueAsPercentageServiceTaxUseCase,
    val saveResumeImageOnDiskUseCase: SaveResumeImageOnDiskUseCase,
)
