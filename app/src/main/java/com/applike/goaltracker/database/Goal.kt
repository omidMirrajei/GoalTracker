package com.applike.goaltracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "goal_tracker_table")
data class Goal(
    @ColumnInfo
    var goalTitle: String,

    @ColumnInfo
    var startTime: Long = System.currentTimeMillis()
): Serializable {

    @PrimaryKey(autoGenerate = true)
    var goalId: Long = 0L
}