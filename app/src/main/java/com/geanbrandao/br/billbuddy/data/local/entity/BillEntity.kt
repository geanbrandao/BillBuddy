package com.geanbrandao.br.billbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "bills")
data class BillEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String, // TODO adicionar status de conta aberta ou fechada
)
