package com.geanbrandao.br.billbuddy.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.geanbrandao.br.billbuddy.data.local.converters.Converters
import com.geanbrandao.br.billbuddy.data.local.dao.AppDao
import com.geanbrandao.br.billbuddy.data.local.entity.BillEntity
import com.geanbrandao.br.billbuddy.data.local.entity.ItemEntity
import com.geanbrandao.br.billbuddy.data.local.entity.UserEntity

const val DB_VERSION = 1
const val DB_NAME = "billbuddy.db"
@Database(
    entities = [BillEntity::class, UserEntity::class, ItemEntity::class],
    exportSchema = true,
    version = DB_VERSION,
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract val appDao: AppDao
}