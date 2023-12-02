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
import com.innovatenestlabs.taskmanager.utils.Constants.ACTION_COMMAND
import com.innovatenestlabs.taskmanager.utils.Constants.ADD_COMMAND
import com.innovatenestlabs.taskmanager.utils.Constants.TASK_ID
import com.innovatenestlabs.taskmanager.utils.Constants.UPDATE_COMMAND
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

    override fun onResume() {
        super.onResume()
        // for clearing existing data
        taskList.clear()
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
                        if (tasks.isNotEmpty()) {
                            binding.rvTaskList.visibility = View.VISIBLE
                            binding.tvLabel.text = getString(R.string.your_tasks)
                            taskList.addAll(tasks)
                        }
                    }

                }

                is Response.Error -> {

                }
            }
        })
        displayTasksViewModel.taskResponse.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Response.Loading -> {}
                is Response.Success -> {
                    taskList.clear()
                    // just call the load task method
                    displayTasksViewModel.loadTaskList()
                }
                is Response.Error -> {}
            }
        })
    }

    private fun bindingEvents() {
        binding.fabAddTask.setOnClickListener {
            val bundle = Bundle().apply { putString(ACTION_COMMAND, ADD_COMMAND) }
            findNavController().navigate(
                R.id.action_displayFragment_to_updateTaskFragment,
                bundle
            )
        }
        // check box event

    }

    private fun setupRecyclerView() {
        taskListAdapter = TaskListAdapter()
        binding.rvTaskList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTaskList.setHasFixedSize(true)
        // now set the list and adapter
        taskListAdapter.submitList(taskList)
        binding.rvTaskList.adapter = taskListAdapter
        taskListAdapter.setOnItemClickListener(object : TaskListAdapter.OnItemClickListener {
            override fun onItemClick(task: Task) {
                val bundle = Bundle()
                bundle.putInt(TASK_ID, task.id)
                // for adding / editing same fragment used
                // so the command key used which command it has to perform
                bundle.putString(ACTION_COMMAND, UPDATE_COMMAND)
                findNavController().navigate(
                    R.id.action_displayFragment_to_updateTaskFragment,
                    bundle
                )
            }

            override fun onCheckBoxClick(task: Task) {
                displayTasksViewModel.updateTask(task)
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}