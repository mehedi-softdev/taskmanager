package com.innovatenestlabs.taskmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.innovatenestlabs.taskmanager.adapters.TaskListAdapter
import com.innovatenestlabs.taskmanager.databinding.FragmentDisplayBinding
import com.innovatenestlabs.taskmanager.utils.Response
import com.innovatenestlabs.taskmanager.viewmodels.DisplayTasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisplayFragment : Fragment() {
    private var _binding: FragmentDisplayBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskListAdapter: TaskListAdapter
    private val displayTasksViewModel by viewModels<DisplayTasksViewModel>()

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
                        if (tasks.isNotEmpty()) {
                            // change the default text of label
                            binding.tvLabel.text = getString(R.string.your_tasks);
                            taskListAdapter.submitList(tasks)
                        }
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
        binding.rvTaskList.adapter = taskListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}