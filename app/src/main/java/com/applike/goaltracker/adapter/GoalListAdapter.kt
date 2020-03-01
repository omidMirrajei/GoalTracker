package com.applike.goaltracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.applike.goaltracker.R
import com.applike.goaltracker.convertLongToDateString
import com.applike.goaltracker.database.Goal
import com.applike.goaltracker.ui.HomeFragmentDirections
import java.util.*

class GoalListAdapter : RecyclerView.Adapter<GoalListAdapter.GoalHolder>() {

    private var goals = emptyList<Goal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalHolder {
        return GoalHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.goal_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GoalHolder, position: Int) {
        val current = goals[position]
        holder.textViewGoal.text = current.goalTitle
        holder.textViewStartTime.text = convertLongToDateString(current.startTime)

        holder.itemView.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToGoalMakerFragment()
            action.goal = goals[position]
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = goals.size

    class GoalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewGoal: TextView = itemView.findViewById(R.id.textView_goal)
        val textViewStartTime: TextView = itemView.findViewById(R.id.textView_startTime)
    }

    internal fun setGoals(goals: List<Goal>) {
        this.goals = goals
        notifyDataSetChanged()
    }
}