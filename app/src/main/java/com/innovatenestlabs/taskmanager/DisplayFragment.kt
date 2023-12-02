package com.innovatenestlabs.taskmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.innovatenestlabs.taskmanager.adapters.TaskListAdapter
import com.innovatenestlabs.taskmanager.databinding.FragmentDisplayBinding
import com.innovatenestlabs.taskmanager.models.Task
import com.innovatenestlabs.taskmanager.utils.Response
import com.innovatenestlabs.taskmanager.viewmodels.DisplayTasksViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class DisplayFragment : Fragment() {
    private var _binding: FragmentDisplayBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskListAdapter: TaskListAdapter
    private val displayTasksViewModel by viewModels<DisplayTasksViewModel>()
    private val taskList = mutableListOf<Task>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDisplayBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        bindingEvents()
        bindingObservers()
    }

    override fun onStart() {
        super.onStart()
        // call the loading function of tasks
        displayTasksViewModel.loadTaskList()
    }

    private fun bindingObservers() {
        displayTasksViewModel.taskListResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            when (it) {
                is Response.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Response.Success -> {
                    it.data?.let { tasks ->
                        binding.rvTaskList.visibility = View.VISIBLE
                        binding.tvLabel.text = getString(R.string.your_tasks)
                        taskList.clear()
                        taskList.addAll(tasks)
                    }

                }

                is Response.Error -> {

                }
            }
        })
    }

    private fun bindingEvents() {
        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_displayFragment_to_updateTaskFragment)
        }
    }

    private fun setupRecyclerView() {
        taskListAdapter = TaskListAdapter()
        binding.rvTaskList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTaskList.setHasFixedSize(true)
        // now set the adapter
        taskListAdapter.submitList(taskList)
        binding.rvTaskList.adapter = taskListAdapter
        taskListAdapter.setOnItemClickListener(object : TaskListAdapter.OnItemClickListener {
            override fun onItemClick(task: Task) {
                // later: action for update
                Toast.makeText(
                    requireContext(), "Task id: ${task.id}", Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}