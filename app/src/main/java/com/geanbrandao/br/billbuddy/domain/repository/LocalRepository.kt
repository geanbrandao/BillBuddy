package com.geanbrandao.br.billbuddy.domain.repository

import com.geanbrandao.br.billbuddy.data.local.entity.BillEntity
import com.geanbrandao.br.billbuddy.data.local.entity.ItemEntity

interface LocalRepository {
    suspend fun createBill(bill: BillEntity): Long
    suspend fun getBills(): List<BillEntity>
    suspend fun getItems(billId: Long): List<ItemEntity>
}