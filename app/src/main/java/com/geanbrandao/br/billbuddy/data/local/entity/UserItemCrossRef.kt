package com.geanbrandao.br.billbuddy.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "user_items_cross_ref",
    primaryKeys = ["userId", "itemId"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["itemId"],
            childColumns = ["itemId"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [
        Index("userId"),
        Index("itemId"),
    ],
)
data class UserItemCrossRef(
    val userId: Long,
    val itemId: Long,
    val dividedValue: Float,
)

data class UserWithItems(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "itemId",
        associateBy = Junction(UserItemCrossRef::class)
    )
    val items: List<ItemEntity>,
)

data class ItemWithUsers(
    @Embedded val item: ItemEntity,
    @Relation(
        parentColumn = "itemId",
        entityColumn = "userId",
        associateBy = Junction(UserItemCrossRef::class),
    )
    val users: List<UserEntity>,
)
