package com.geanbrandao.br.billbuddy.data.local.repository

import com.geanbrandao.br.billbuddy.data.local.dao.AppDao
import com.geanbrandao.br.billbuddy.data.local.entity.BillEntity
import com.geanbrandao.br.billbuddy.data.local.entity.ItemEntity
import com.geanbrandao.br.billbuddy.domain.repository.LocalRepository
import org.koin.core.annotation.Single

@Single
class LocalRepositoryImpl(
    private val dao: AppDao
): LocalRepository {

    override suspend fun createBill(bill: BillEntity): Long {
        return dao.insertBill(bill)
    }

    override suspend fun getBills(): List<BillEntity> {
        return dao.getBills()
    }

    override suspend fun getItems(billId: Long): List<ItemEntity> {
        return dao.getItems(billId)
    }
}