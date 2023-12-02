package com.innovatenestlabs.taskmanager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.innovatenestlabs.taskmanager.databinding.FragmentTaskAddOrUpdateBinding
import com.innovatenestlabs.taskmanager.models.Task
import com.innovatenestlabs.taskmanager.utils.AppConverters
import com.innovatenestlabs.taskmanager.utils.Constants.ACTION_COMMAND
import com.innovatenestlabs.taskmanager.utils.Constants.ADD_COMMAND
import com.innovatenestlabs.taskmanager.utils.Constants.TASK_ID
import com.innovatenestlabs.taskmanager.utils.Constants.UPDATE_COMMAND
import com.innovatenestlabs.taskmanager.utils.Response
import com.innovatenestlabs.taskmanager.viewmodels.TaskAddOrUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class TaskAddOrUpdateFragment : Fragment() {
    private var _binding: FragmentTaskAddOrUpdateBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectDateTime: Calendar

    private val taskAddOrUpdateViewModel by viewModels<TaskAddOrUpdateViewModel>()
    private var actionCommand: String? = null
    private var task: Task? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskAddOrUpdateBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionCommand = arguments?.getString(ACTION_COMMAND)
        actionCommand?.let {
            if (it == UPDATE_COMMAND) {
                val taskId = arguments?.getInt(TASK_ID)
                // fetch task data from storage based on id
                taskId?.let {
                    taskAddOrUpdateViewModel.getTaskById(taskId)
                }
            } else if (it == ADD_COMMAND) {
                // default initialization
                initialize(false)
            } else {
                // redirect to previous fragment
                findNavController().popBackStack()
            }
        }
        bindingEvents()
        bindingObservers()
    }

    private fun bindingObservers() {
        // this live data used on adding or updating
        taskAddOrUpdateViewModel.taskResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            when (it) {
                is Response.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvErrorMessage.visibility =
                        View.GONE // no need to show message in loading time
                }

                is Response.Success -> {
                    actionCommand?.let {
                        if (it == UPDATE_COMMAND) {
                            Toast.makeText(
                                requireContext(),
                                "Task updated successfully!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Task added successfully!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        findNavController().popBackStack() // reversing the view
                    }

                }

                is Response.Error -> {
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.tvErrorMessage.text = it.message.toString()
                }
            }
        })
        // this live data only for when fetching task data
        taskAddOrUpdateViewModel.task.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Response.Loading -> {}
                is Response.Success -> {
                    task = it.data // initialize to class variable
                    // update initialization
                    initialize(true)
                }

                is Response.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    findNavController().popBackStack() // this is if somehow any error occur
                }
            }
        })
    }

    private fun initialize(isUpdate: Boolean) {
        // creating calendar instance
        selectDateTime = Calendar.getInstance()
        if (isUpdate) {
            binding.tvLabel.text = getString(R.string.update_task)
            this.task?.let {
                binding.etTaskName.setText(it.name)
                binding.etTaskDesc.setText(it.desc)
                binding.btnDateTimePicker.text = AppConverters.getDateTime(it.date)
                selectDateTime.time = it.date // setting user selected value
            }
            binding.btnSaveTask.text = getString(R.string.update_task)
        }
    }

    private fun bindingEvents() {
        binding.btnDateTimePicker.setOnClickListener {
            btnDateTimePickerEvent()
        }
        binding.btnSaveTask.setOnClickListener {
            val validate = validateUserInput()
            if (validate.first) {
                actionCommand?.let {
                    if(it == UPDATE_COMMAND) {
                        task?.let { t ->
                            taskAddOrUpdateViewModel.updateTask(t)
                        }
                    }else if(it == ADD_COMMAND) {
                        taskAddOrUpdateViewModel.insertTask(getTask())
                    } else {
                        // do nothing
                    }
                }
            } else {
                binding.tvErrorMessage.visibility = View.VISIBLE
                // show the error message
                binding.tvErrorMessage.text = validate.second
            }
        }
    }


    private fun btnDateTimePickerEvent() {
        val currentYear = selectDateTime.get(Calendar.YEAR)
        val currentMonth = selectDateTime.get(Calendar.MONTH)
        val currentDay = selectDateTime.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, year, month, day ->
                // now set the date as selected
                selectDateTime.set(Calendar.YEAR, year)
                selectDateTime.set(Calendar.MONTH, month)
                selectDateTime.set(Calendar.DAY_OF_MONTH, day)
                // now call the time picker dialogue
                showTimePicker()
            }, currentYear, currentMonth, currentDay
        )
        // showing the dialogue
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val currentHour = selectDateTime.get(Calendar.HOUR_OF_DAY)
        val currentMinute = selectDateTime.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hour, minute ->
                selectDateTime.set(Calendar.HOUR_OF_DAY, hour)
                selectDateTime.set(Calendar.MINUTE, minute)
                // set selected date time to button text as string
                binding.btnDateTimePicker.text = AppConverters.getDateTime(selectDateTime.time)

            },
            currentHour,
            currentMinute,
            true // for 24 hour format
        )
        // showing the time picker dialogue
        timePickerDialog.show()
    }

    // fun for validate user input
    private fun validateUserInput(): Pair<Boolean, String> {
        val task = getTask()
        return taskAddOrUpdateViewModel.validateTaskInput(task)
    }

    private fun getTask(): Task {
        if (actionCommand!= null && actionCommand == UPDATE_COMMAND) {
            this.task?.name = binding.etTaskName.text.toString()
            this.task?.desc = binding.etTaskDesc.text.toString()
            // reassign date also because user can update their date picker data
            this.task?.date = selectDateTime.time
            return this.task!!
        }else {
            val task = Task(
                0, binding.etTaskName.text.toString(),
                binding.etTaskDesc.text.toString(),
                selectDateTime.time, false
            )
            return task
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}