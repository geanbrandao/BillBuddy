package com.geanbrandao.br.billbuddy.domain.repository

import com.geanbrandao.br.billbuddy.data.local.entity.BillEntity
import com.geanbrandao.br.billbuddy.data.local.entity.ItemEntity
import com.geanbrandao.br.billbuddy.data.local.entity.UserEntity
import com.geanbrandao.br.billbuddy.data.local.entity.UserItemCrossRef
import com.geanbrandao.br.billbuddy.data.local.entity.UserWithItemDividedValue

interface LocalRepository {
    suspend fun createBill(bill: BillEntity): Long
    suspend fun createItem(item: ItemEntity): Long
    suspend fun createItemCrossRef(crossRef: UserItemCrossRef)
    suspend fun getBills(): List<BillEntity>
    suspend fun getItems(billId: Long): List<ItemEntity>
    suspend fun getItemsDividedValue(billId: Long): List<UserWithItemDividedValue>
    suspend fun getBill(billId: Long): BillEntity
    suspend fun getPersons(billId: Long): List<UserEntity>
}
