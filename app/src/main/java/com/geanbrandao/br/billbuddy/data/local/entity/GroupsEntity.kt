package com.geanbrandao.br.billbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "groups",
)
data class GroupsEntity(
    @PrimaryKey(autoGenerate = true) val groupId: Long,
    val name: String,
)