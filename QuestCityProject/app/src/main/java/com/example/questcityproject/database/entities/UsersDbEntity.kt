package com.example.questcityproject.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)
data class UsersDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
//    @ColumnInfo(name = "point_id") val pointId: Long,
//    @ColumnInfo(name = "quest_id") val questId: Long,
//    @ColumnInfo(name = "achievement_id") val achievementId: Long,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
)
