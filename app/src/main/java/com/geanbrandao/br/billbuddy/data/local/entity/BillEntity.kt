package com.geanbrandao.br.billbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geanbrandao.br.billbuddy.domain.model.BillStatus


@Entity(tableName = "bills")
data class BillEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val status: BillStatus,
)
