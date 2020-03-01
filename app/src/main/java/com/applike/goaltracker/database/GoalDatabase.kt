package com.applike.goaltracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Goal::class], version = 1, exportSchema = false)
abstract class GoalDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract fun goalDao(): GoalDao

    private class GoalDatabaseCallback : RoomDatabase.Callback()

    companion object {

        @Volatile
        private var INSTANCE: GoalDatabase? = null

        fun getInstance(context: Context): GoalDatabase {
            synchronized(this) {
                var instance =
                    INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GoalDatabase::class.java,
                        "goal_history_database"
                    )
                        .addCallback(GoalDatabaseCallback())
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}