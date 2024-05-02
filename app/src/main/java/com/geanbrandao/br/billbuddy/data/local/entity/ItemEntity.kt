package com.geanbrandao.br.billbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = BillEntity::class,
            parentColumns = ["id"],
            childColumns = ["billId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["billId"])],
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val value: Float,
    val billId: Int,
    val userId: Int, // todo remover o user ID e criar uma relação many to many entre item e usuário
)
