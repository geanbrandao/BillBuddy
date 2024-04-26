package com.geanbrandao.br.billbuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geanbrandao.br.billbuddy.data.local.entity.BillEntity
import com.geanbrandao.br.billbuddy.data.local.entity.ItemEntity
import com.geanbrandao.br.billbuddy.data.local.entity.UserEntity

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill: BillEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity)

    @Query("SELECT * FROM bills")
    suspend fun getBills(): List<BillEntity>

    @Query("SELECT * FROM users WHERE billId = :billId")
    suspend fun getUsers(billId: Int): List<UserEntity>

    @Query("SELECT * FROM items WHERE billId = :billId")
    suspend fun getItems(billId: Int): List<ItemEntity>
}