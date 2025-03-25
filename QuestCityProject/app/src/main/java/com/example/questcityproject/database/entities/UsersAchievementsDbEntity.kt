package com.example.questcityproject.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users_achievements",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = AchievementsDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["achievement_id"]
        ),
        ForeignKey(
            entity = UsersDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"]
        )
    ]
)
data class UsersAchievementsDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "achievement_id") val achievementId: Long
)
