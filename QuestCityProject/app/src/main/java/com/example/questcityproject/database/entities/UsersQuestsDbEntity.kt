package com.example.questcityproject.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users_quests",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = QuestsDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["quest_id"]
        ),
        ForeignKey(
            entity = UsersDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"]
        )
    ]
)
data class UsersQuestsDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "quest_id") val questId: Long,
    @ColumnInfo(name = "label") val label: String,
)
