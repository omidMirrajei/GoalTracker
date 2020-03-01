package com.applike.goaltracker.database

import androidx.lifecycle.LiveData

class GoalRepository(private val goalDao: GoalDao) {

    val allGoals: LiveData<List<Goal>> = goalDao.getAllGoals()

    suspend fun addGoal(goal: Goal) {
        goalDao.addGoal(goal)
    }

    suspend fun updateGoal(goal: Goal) {
        goalDao.updateGoal(goal)
    }

    suspend fun deleteGoal(goal: Goal) {
        goalDao.deleteGoal(goal)
    }

    suspend fun clear() {
        goalDao.clear()
    }
}