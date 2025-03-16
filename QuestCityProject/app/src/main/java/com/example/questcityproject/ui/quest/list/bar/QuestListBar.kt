package com.example.questcityproject.ui.quest.list.bar

class QuestListBar(
    val id: Int,
    val primaryName: String,
    val secondaryName: String,
    val isActive: Boolean,
    val numPointsAll: Int,
    val numPointsVisited: Int = 0) {

    operator fun component1(): QuestListBar {
        return this
    }
}