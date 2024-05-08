package com.geanbrandao.br.billbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    foreignKeys = [
        ForeignKey(
            entity = BillEntity::class,
            parentColumns = ["id"],
            childColumns = ["billId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index(value = ["billId"])],
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val userId: Long,
    val name: String,
    val billId: Long,
)