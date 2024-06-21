package com.geanbrandao.br.billbuddy.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "group_user_cross_ref",
    primaryKeys = ["groupId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = GroupsEntity::class,
            parentColumns = ["groupId"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index("groupId"),
        Index("userId"),
    ],
)
data class GroupUserCrossRefEntity(
    val groupId: Long,
    val userId: Long,
)

data class GroupWithUsers(
    @Embedded val group: GroupsEntity,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "userId",
        associateBy = Junction(GroupUserCrossRefEntity::class),
    )
    val users: List<UserEntity>,
)

data class UserWithGroups(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "groupId",
        associateBy = Junction(GroupUserCrossRefEntity::class),
    )
    val groups: List<GroupsEntity>,
)