package com.geanbrandao.br.billbuddy.data.local.repository

import com.geanbrandao.br.billbuddy.data.local.dao.AppDao
import com.geanbrandao.br.billbuddy.data.local.entity.BillEntity
import com.geanbrandao.br.billbuddy.data.local.entity.ItemEntity
import com.geanbrandao.br.billbuddy.data.local.entity.UserEntity
import com.geanbrandao.br.billbuddy.data.local.entity.UserItemCrossRef
import com.geanbrandao.br.billbuddy.data.local.entity.UserWithItemDividedValue
import com.geanbrandao.br.billbuddy.domain.repository.LocalRepository
import org.koin.core.annotation.Single

@Single
class LocalRepositoryImpl(
    private val dao: AppDao
): LocalRepository {

    override suspend fun createBill(bill: BillEntity): Long {
        return dao.insertBill(bill)
    }

    override suspend fun createItem(item: ItemEntity): Long {
        return dao.insertItem(item)
    }

    override suspend fun createItemCrossRef(crossRef: UserItemCrossRef) {
        dao.insertUserItemCrossRef(crossRef)
    }

    override suspend fun getBills(): List<BillEntity> {
        return dao.getBills()
    }

    override suspend fun getItems(billId: Long): List<ItemEntity> {
        return dao.getItems(billId)
    }

    override suspend fun getItemsDividedValue(billId: Long): List<UserWithItemDividedValue> {
        return dao.getUsersWithItemsAndDividedValues(billId)
    }

    override suspend fun getBill(billId: Long): BillEntity {
        return dao.getBill(billId = billId)
    }

    override suspend fun getPersons(billId: Long): List<UserEntity> {
        return dao.getUsers(billId = billId)
    }

    override suspend fun removeItem(itemId: Long?) {
        return dao.removeItem(itemId = itemId)
    }
}