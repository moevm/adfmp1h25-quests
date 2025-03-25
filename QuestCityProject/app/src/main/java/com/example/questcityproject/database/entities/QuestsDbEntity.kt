package com.example.questcityproject.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "quests"
)
data class QuestsDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "primary_title") val primaryTitle: String,
    @ColumnInfo(name = "secondary_title") val secondaryTitle: String,
    @ColumnInfo(name = "description") val description: String,
)
