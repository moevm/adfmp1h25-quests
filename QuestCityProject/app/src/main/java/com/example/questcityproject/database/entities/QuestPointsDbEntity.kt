package com.example.questcityproject.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "quests",
    indices = [androidx.room.Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = QuestsDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["quest_id"]
        )
    ]
)
data class QuestPointsDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "quest_id") val questId: Long,
    @ColumnInfo(name = "a_latitude") val aLatitude: Double,
    @ColumnInfo(name = "a_longitude") val aLongitude: Double,
    @ColumnInfo(name = "title") val title: String,
)