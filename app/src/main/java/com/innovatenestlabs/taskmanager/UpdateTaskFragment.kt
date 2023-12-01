package com.innovatenestlabs.taskmanager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.innovatenestlabs.taskmanager.databinding.FragmentUpdateTaskBinding
import com.innovatenestlabs.taskmanager.models.Task
import com.innovatenestlabs.taskmanager.utils.AppConverters
import com.innovatenestlabs.taskmanager.viewmodels.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class UpdateTaskFragment : Fragment() {
    private var _binding: FragmentUpdateTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectDateTime: Calendar

    private val taskViewModel by viewModels<TaskViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateTaskBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        bindingEvents()
    }

    private fun initialize() {
        selectDateTime = Calendar.getInstance()
    }

    private fun bindingEvents() {
        binding.btnDateTimePicker.setOnClickListener {
            btnDateTimePickerEvent()
        }
        binding.btnSaveTask.setOnClickListener {
            val validate = validateUserInput()
            if (validate.first) {
                taskViewModel.insertTask(getTask())
            } else {
                binding.tvErrorMessage.visibility = View.VISIBLE
                binding.tvErrorMessage.setTextColor(Color.RED)
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
    fun validateUserInput(): Pair<Boolean, String> {
        val task = getTask()
        return taskViewModel.validateTaskInput(task)
    }

    private fun getTask(): Task {
        val task = Task(
            0, binding.etTaskName.text.toString(),
            binding.etTaskDesc.text.toString(),
            selectDateTime.time, false
        )
        return task
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}