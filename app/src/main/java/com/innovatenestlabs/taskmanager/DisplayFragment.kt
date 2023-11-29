package com.innovatenestlabs.taskmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.innovatenestlabs.taskmanager.adapters.TaskListAdapter
import com.innovatenestlabs.taskmanager.databinding.FragmentDisplayBinding

class DisplayFragment : Fragment() {
    private var _binding: FragmentDisplayBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskListAdapter: TaskListAdapter

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
    }

    private fun setupRecyclerView() {
        binding.rvTaskList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTaskList.setHasFixedSize(true)
        taskListAdapter = TaskListAdapter()
        // now set the adapter
        binding.rvTaskList.adapter = taskListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}