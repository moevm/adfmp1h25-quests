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
            entity = QuestPointsDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["point_id"]
        ),
        ForeignKey(
            entity = UsersDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"]
        )
    ]
)
data class UsersQuestPointsDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "point_id") val pointId: Long,
)
