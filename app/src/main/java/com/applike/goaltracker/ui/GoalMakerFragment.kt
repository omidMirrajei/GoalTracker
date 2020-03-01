package com.applike.goaltracker.ui

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController

import com.applike.goaltracker.R
import com.applike.goaltracker.database.Goal
import com.applike.goaltracker.databinding.FragmentGoalMakerBinding
import com.applike.goaltracker.model.GoalViewModel
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.fragment_goal_maker.*
import kotlinx.coroutines.launch


class GoalMakerFragment : Fragment() {

    private lateinit var goalViewModel: GoalViewModel
    var goal: Goal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGoalMakerBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_goal_maker, container, false)


        goalViewModel = ViewModelProvider(this).get(GoalViewModel::class.java)

        arguments?.let {
            goal = GoalMakerFragmentArgs.fromBundle(it).goal
            binding.editTextAddGoal.setText(goal?.goalTitle)
        }

        if (goal != null) {
            binding.buttonSubmit.text = "Update your goal"
        }

        binding.buttonSubmit.setOnClickListener {
            val goalTitle = editText_addGoal.text.trim().toString()
            val mGoal = Goal(goalTitle)

            if (goal == null) {
                if (goalTitle.isEmpty()) {
                    editText_addGoal.error = "Insert a Goal title"
                    return@setOnClickListener
                } else {
//                    val mGoal = Goal(goalTitle)
                    goalViewModel.addGoal(mGoal)
                }
            } else {
                mGoal.goalId = goal!!.goalId
                goalViewModel.updateGoal(mGoal)
            }

            it.findNavController()
                .navigate(
                    GoalMakerFragmentDirections
                        .actionGoalMakerFragmentToHomeFragment()
                )
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun deleteRow() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation.")
            setPositiveButton("OK") { _, _ ->
                goalViewModel.deleteGoal(goal!!)
                Snackbar.make(view!!, "Goal : ${goal!!.goalTitle} deleted", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                val action = GoalMakerFragmentDirections.actionGoalMakerFragmentToHomeFragment()
                Navigation.findNavController(view!!).navigate(action)
            }
            setNegativeButton("CANCEL") { _, _ -> }
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (goal == null) menu.findItem(R.id.clear_db).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clear_db -> deleteRow()
        }
        return super.onOptionsItemSelected(item)
    }
}
