package com.example.questcityproject.data

import android.content.Context
import android.util.Log

object SampleDataInitializer {
    private const val TAG = "SampleDataInitializer"

    fun initializeSampleData(context: Context) {
        val dbHelper = DatabaseHelper(context)

        try {
            // Add sample users if none exist
            val testUser = dbHelper.getUser("test")
            if (testUser == null) {
                dbHelper.addUser("test", "test@example.com", "password")
                dbHelper.addUser("admin", "admin@example.com", "admin123")
                Log.d(TAG, "Sample users added")
            }

            // Add sample quests
            val quests = dbHelper.getAllQuests()
            if (quests.isEmpty()) {
                val questId1 = dbHelper.addQuest("Мосты Петербурга", 5, "Исследуйте знаменитые мосты Санкт-Петербурга")
                val questId2 = dbHelper.addQuest("Университеты", 2, "Посетите известные университеты города")

                // Add landmarks for the first quest
                if (questId1 != -1L) {
                    dbHelper.addLandmark(questId1.toInt(), "Дворцовый мост", 59.941326, 30.307736, "Дворцовый мост")
                    dbHelper.addLandmark(questId1.toInt(), "Троицкий мост", 59.948637, 30.327564, "Троицкий мост")
                    dbHelper.addLandmark(questId1.toInt(), "Благовещенский мост", 59.934892, 30.289371, "Благовещенский мост")
                    dbHelper.addLandmark(questId1.toInt(), "Литейный мост", 59.952143, 30.349531, "Литейный мост")
                    dbHelper.addLandmark(questId1.toInt(), "Большеохтинский мост", 59.942731, 30.401357, "Большеохтинский мост")
                }

                // Add landmarks for the second quest
                if (questId2 != -1L) {
                    dbHelper.addLandmark(questId2.toInt(), "ЛЭТИ", 59.973023, 30.324240, "ЛЭТИ")
                    dbHelper.addLandmark(questId2.toInt(), "ИТМО", 59.956435, 30.308726, "ИТМО")
                }

                Log.d(TAG, "Sample quests and landmarks added")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing sample data: ${e.message}")
        }
    }
}