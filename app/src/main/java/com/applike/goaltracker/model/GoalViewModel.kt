package com.applike.goaltracker.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.applike.goaltracker.database.Goal
import com.applike.goaltracker.database.GoalDatabase
import com.applike.goaltracker.database.GoalRepository
import kotlinx.coroutines.*

class GoalViewModel(
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val repository: GoalRepository
    val allGoals: LiveData<List<Goal>>

    init {
        val goalDao = GoalDatabase.getInstance(application).goalDao()
        repository = GoalRepository(goalDao)
        allGoals = repository.allGoals
    }

    fun addGoal(goal: Goal) = viewModelScope.launch {
        repository.addGoal(goal)
    }

    fun updateGoal(goal: Goal) = viewModelScope.launch {
        repository.updateGoal(goal)
    }

    fun deleteGoal(goal: Goal) = viewModelScope.launch {
        repository.deleteGoal(goal)
    }

    fun clearAllDb() = uiScope.launch {
        repository.clear()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}