package com.applike.goaltracker.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.applike.goaltracker.R
import com.applike.goaltracker.adapter.GoalListAdapter
import com.applike.goaltracker.databinding.FragmentHomeBinding
import com.applike.goaltracker.model.GoalViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var goalViewModel: GoalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        goalViewModel = ViewModelProvider(this).get(GoalViewModel::class.java)

        binding.fabAdd.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_goalMakerFragment)
        )

        val adapter = GoalListAdapter()
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(activity!!)
            goalViewModel.allGoals.observe(viewLifecycleOwner, Observer { goals ->
                goals.let { adapter.setGoals(it) }
            })
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun clearDatabase() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure want to clear Database?")
            setMessage("You cannot undo this operation.")
            setPositiveButton("OK") { _, _ ->
                goalViewModel.clearAllDb()
                Snackbar.make(view!!, "All information was cleared", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            }
            setNegativeButton("CANCEL") { _, _ -> }
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_db) {
            clearDatabase()
        }
        return super.onOptionsItemSelected(item)

    }
}
