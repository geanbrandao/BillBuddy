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

    @Query("SELECT * FROM users WHERE billId = :billId")
    suspend fun getUsers(billId: Int): List<UserEntity>

    @Query("SELECT * FROM items WHERE billId = :billId")
    suspend fun getItems(billId: Int): List<ItemEntity>

    @Transaction
    @Query("SELECT * FROM items")
    suspend fun getItemsWithUsers(): List<ItemWithUsers>

    @Transaction
    @Query("SELECT * FROM users")
    suspend fun getUsersWithItems(): List<UserWithItems>

}