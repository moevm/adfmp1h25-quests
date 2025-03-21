package com.example.questcityproject.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val TAG = "DatabaseHelper"

        // Database Info
        private const val DATABASE_NAME = "QuestCityDatabase"
        private const val DATABASE_VERSION = 1

        // Table Names
        const val TABLE_USERS = "users"
        const val TABLE_QUESTS = "quests"
        const val TABLE_LANDMARKS = "landmarks"

        // User Table Columns
        const val KEY_USER_ID = "id"
        const val KEY_USER_LOGIN = "login"
        const val KEY_USER_EMAIL = "email"
        const val KEY_USER_PASSWORD = "password"

        // Quest Table Columns
        const val KEY_QUEST_ID = "id"
        const val KEY_QUEST_TITLE = "title"
        const val KEY_QUEST_LANDMARKS_NUMBER = "landmarks_number"
        const val KEY_QUEST_DESCRIPTION = "description"

        // Landmark Table Columns
        const val KEY_LANDMARK_ID = "id"
        const val KEY_LANDMARK_QUEST_ID = "quest_id"
        const val KEY_LANDMARK_NAME = "name"
        const val KEY_LANDMARK_LATITUDE = "latitude"
        const val KEY_LANDMARK_LONGITUDE = "longitude"
        const val KEY_LANDMARK_TITLE = "title"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create Users Table
        val CREATE_USERS_TABLE = """
            CREATE TABLE $TABLE_USERS (
                $KEY_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_USER_LOGIN TEXT UNIQUE NOT NULL,
                $KEY_USER_EMAIL TEXT UNIQUE NOT NULL,
                $KEY_USER_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()

        // Create Quests Table
        val CREATE_QUESTS_TABLE = """
            CREATE TABLE $TABLE_QUESTS (
                $KEY_QUEST_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_QUEST_TITLE TEXT NOT NULL,
                $KEY_QUEST_LANDMARKS_NUMBER INTEGER NOT NULL,
                $KEY_QUEST_DESCRIPTION TEXT
            )
        """.trimIndent()

        // Create Landmarks Table
        val CREATE_LANDMARKS_TABLE = """
            CREATE TABLE $TABLE_LANDMARKS (
                $KEY_LANDMARK_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_LANDMARK_QUEST_ID INTEGER NOT NULL,
                $KEY_LANDMARK_NAME TEXT NOT NULL,
                $KEY_LANDMARK_LATITUDE REAL NOT NULL,
                $KEY_LANDMARK_LONGITUDE REAL NOT NULL,
                $KEY_LANDMARK_TITLE TEXT,
                FOREIGN KEY ($KEY_LANDMARK_QUEST_ID) REFERENCES $TABLE_QUESTS($KEY_QUEST_ID)
            )
        """.trimIndent()

        db.execSQL(CREATE_USERS_TABLE)
        db.execSQL(CREATE_QUESTS_TABLE)
        db.execSQL(CREATE_LANDMARKS_TABLE)

        Log.d(TAG, "Database tables created")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            // Drop older tables if existed
            db.execSQL("DROP TABLE IF EXISTS $TABLE_LANDMARKS")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_QUESTS")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")

            // Create tables again
            onCreate(db)
        }
    }

    // User CRUD Operations

    /**
     * Add a new user to the database
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    fun addUser(login: String, email: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_USER_LOGIN, login)
        values.put(KEY_USER_EMAIL, email)
        values.put(KEY_USER_PASSWORD, password)

        try {
            val id = db.insertOrThrow(TABLE_USERS, null, values)
            Log.d(TAG, "User added with ID: $id")
            return id
        } catch (e: Exception) {
            Log.e(TAG, "Error adding user: ${e.message}")
            return -1
        } finally {
            db.close()
        }
    }

    /**
     * Check if a user exists with the given login and password
     * @return true if user exists, false otherwise
     */
    fun checkUser(login: String, password: String): Boolean {
        val db = this.readableDatabase
        val selection = "$KEY_USER_LOGIN = ? AND $KEY_USER_PASSWORD = ?"
        val selectionArgs = arrayOf(login, password)

        val cursor = db.query(
            TABLE_USERS,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val userExists = cursor.count > 0
        cursor.close()
        db.close()
        return userExists
    }

    /**
     * Check if a login already exists
     * @return true if login exists, false otherwise
     */
    fun checkLoginExists(login: String): Boolean {
        val db = this.readableDatabase
        val selection = "$KEY_USER_LOGIN = ?"
        val selectionArgs = arrayOf(login)

        val cursor = db.query(
            TABLE_USERS,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val loginExists = cursor.count > 0
        cursor.close()
        db.close()
        return loginExists
    }

    /**
     * Check if an email already exists
     * @return true if email exists, false otherwise
     */
    fun checkEmailExists(email: String): Boolean {
        val db = this.readableDatabase
        val selection = "$KEY_USER_EMAIL = ?"
        val selectionArgs = arrayOf(email)

        val cursor = db.query(
            TABLE_USERS,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val emailExists = cursor.count > 0
        cursor.close()
        db.close()
        return emailExists
    }

    /**
     * Get user by login
     * @return User object or null if not found
     */
    fun getUser(login: String): User? {
        val db = this.readableDatabase
        val selection = "$KEY_USER_LOGIN = ?"
        val selectionArgs = arrayOf(login)

        val cursor = db.query(
            TABLE_USERS,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var user: User? = null
        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(KEY_USER_ID)
            val loginIndex = cursor.getColumnIndex(KEY_USER_LOGIN)
            val emailIndex = cursor.getColumnIndex(KEY_USER_EMAIL)
            val passwordIndex = cursor.getColumnIndex(KEY_USER_PASSWORD)

            if (idIndex != -1 && loginIndex != -1 && emailIndex != -1 && passwordIndex != -1) {
                user = User(
                    id = cursor.getInt(idIndex),
                    login = cursor.getString(loginIndex),
                    email = cursor.getString(emailIndex),
                    password = cursor.getString(passwordIndex)
                )
            }
        }

        cursor.close()
        db.close()
        return user
    }

    // Quest CRUD Operations

    fun addQuest(title: String, landmarksNumber: Int, description: String?): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_QUEST_TITLE, title)
        values.put(KEY_QUEST_LANDMARKS_NUMBER, landmarksNumber)
        values.put(KEY_QUEST_DESCRIPTION, description)

        val id = db.insert(TABLE_QUESTS, null, values)
        db.close()
        return id
    }

    fun getAllQuests(): List<Quest> {
        val quests = mutableListOf<Quest>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_QUESTS,
            null,
            null,
            null,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex(KEY_QUEST_ID)
                val titleIndex = cursor.getColumnIndex(KEY_QUEST_TITLE)
                val landmarksNumberIndex = cursor.getColumnIndex(KEY_QUEST_LANDMARKS_NUMBER)
                val descriptionIndex = cursor.getColumnIndex(KEY_QUEST_DESCRIPTION)

                if (idIndex != -1 && titleIndex != -1 && landmarksNumberIndex != -1 && descriptionIndex != -1) {
                    val quest = Quest(
                        id = cursor.getInt(idIndex),
                        title = cursor.getString(titleIndex),
                        landmarksNumber = cursor.getInt(landmarksNumberIndex),
                        description = cursor.getString(descriptionIndex)
                    )
                    quests.add(quest)
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return quests
    }

    // Landmark CRUD Operations

    fun addLandmark(questId: Int, name: String, latitude: Double, longitude: Double, title: String?): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_LANDMARK_QUEST_ID, questId)
        values.put(KEY_LANDMARK_NAME, name)
        values.put(KEY_LANDMARK_LATITUDE, latitude)
        values.put(KEY_LANDMARK_LONGITUDE, longitude)
        values.put(KEY_LANDMARK_TITLE, title)

        val id = db.insert(TABLE_LANDMARKS, null, values)
        db.close()
        return id
    }

    fun getLandmarksByQuestId(questId: Int): List<Landmark> {
        val landmarks = mutableListOf<Landmark>()
        val db = this.readableDatabase
        val selection = "$KEY_LANDMARK_QUEST_ID = ?"
        val selectionArgs = arrayOf(questId.toString())

        val cursor = db.query(
            TABLE_LANDMARKS,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex(KEY_LANDMARK_ID)
                val questIdIndex = cursor.getColumnIndex(KEY_LANDMARK_QUEST_ID)
                val nameIndex = cursor.getColumnIndex(KEY_LANDMARK_NAME)
                val latitudeIndex = cursor.getColumnIndex(KEY_LANDMARK_LATITUDE)
                val longitudeIndex = cursor.getColumnIndex(KEY_LANDMARK_LONGITUDE)
                val titleIndex = cursor.getColumnIndex(KEY_LANDMARK_TITLE)

                if (idIndex != -1 && questIdIndex != -1 && nameIndex != -1 &&
                    latitudeIndex != -1 && longitudeIndex != -1 && titleIndex != -1) {
                    val landmark = Landmark(
                        id = cursor.getInt(idIndex),
                        questId = cursor.getInt(questIdIndex),
                        name = cursor.getString(nameIndex),
                        latitude = cursor.getDouble(latitudeIndex),
                        longitude = cursor.getDouble(longitudeIndex),
                        title = cursor.getString(titleIndex)
                    )
                    landmarks.add(landmark)
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return landmarks
    }
}