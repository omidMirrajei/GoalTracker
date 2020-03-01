package com.applike.goaltracker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GoalDao {

    @Insert
    suspend fun addGoal(goal: Goal)

    @Update
    suspend fun updateGoal(goal: Goal)

    @Query("SELECT * FROM goal_tracker_table ORDER BY goalId DESC")
    fun getAllGoals(): LiveData<List<Goal>>

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("DELETE FROM goal_tracker_table")
    suspend fun clear()


}