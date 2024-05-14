package com.geanbrandao.br.billbuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.geanbrandao.br.billbuddy.data.local.entity.BillEntity
import com.geanbrandao.br.billbuddy.data.local.entity.ItemEntity
import com.geanbrandao.br.billbuddy.data.local.entity.ItemWithUsers
import com.geanbrandao.br.billbuddy.data.local.entity.UserEntity
import com.geanbrandao.br.billbuddy.data.local.entity.UserItemCrossRef
import com.geanbrandao.br.billbuddy.data.local.entity.UserWithItemDividedValue
import com.geanbrandao.br.billbuddy.data.local.entity.UserWithItems

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill: BillEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserItemCrossRef(crossRef: UserItemCrossRef)

    @Query("SELECT * FROM bills")
    suspend fun getBills(): List<BillEntity>

    @Query("SELECT * FROM bills WHERE id = :billId")
    suspend fun getBill(billId: Long): BillEntity

    @Query("SELECT * FROM users WHERE billId = :billId")
    suspend fun getUsers(billId: Long): List<UserEntity>

    @Query("SELECT * FROM items WHERE billId = :billId")
    suspend fun getItems(billId: Long): List<ItemEntity>

    @Query("DELETE FROM items WHERE itemId = :itemId")
    suspend fun removeItem(itemId: Long?)

    @Query("DELETE from bills WHERE id = :billId")
    suspend fun removeBill(billId: Long?)

    @Transaction
    @Query("SELECT * FROM items")
    suspend fun getItemsWithUsers(): List<ItemWithUsers>

    @Transaction
    @Query("SELECT * FROM users")
    suspend fun getUsersWithItems(): List<UserWithItems>

    @Transaction
    @Query("SELECT users.name as userName, users.userId, items.itemId, items.name as itemName, user_items_cross_ref.dividedValue as value " +
            "FROM users, items, user_items_cross_ref " +
            "WHERE users.userId = user_items_cross_ref.userId " +
            "AND items.itemId = user_items_cross_ref.itemId " +
            "AND items.billId = :billId AND users.billId = :billId")
    suspend fun getUsersWithItemsAndDividedValues(billId: Long): List<UserWithItemDividedValue>
}