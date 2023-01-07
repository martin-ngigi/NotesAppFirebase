package com.example.notesappfirebase.presentation.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.notesappfirebase.R
import com.example.notesappfirebase.data.models.Task
import com.example.notesappfirebase.databinding.FragmentCreateTaskBinding
import com.example.notesappfirebase.presentation.ui.auth.AuthViewModel
import com.example.notesappfirebase.util.UiState
import com.example.notesappfirebase.util.hide
import com.example.notesappfirebase.util.show
import com.example.notesappfirebase.util.toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateTaskFragment (private  val task: Task?= null): BottomSheetDialogFragment(){
    val TAG = "CreateTaskFragment"
    lateinit var  binding: FragmentCreateTaskBinding
    private val viewModel: TaskViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val closeFunction: ((Boolean) -> Unit)? = null
    private var isSuccessAddTask: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        task?.let { binding.taskEt.setText(it.description) }

        binding.cancel.setOnClickListener{
            this.dismiss()
        }

        binding.done.setOnClickListener{
            if (validation()){
                if (task == null){
                    viewModel.addTask(getTask())
                }
                else{
                    task.description = binding.taskEt.text.toString()
                    //viewModel.updateTask(task)
                }
            }
        }
        observer()
    }

    private fun observer() {
        viewModel.addTask.observe(viewLifecycleOwner){ state ->
            when(state){
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    isSuccessAddTask = true
                    binding.progressBar.hide()
                    toast(state.data.second)
                    this.dismiss()
                }
            }
        }
    }

    private fun getTask(): Task {
        val sdf = SimpleDateFormat("dd MMM yyyy . hh:mm a")
        return Task(
            id= "",
            description = binding.taskEt.text.toString(),
            date = sdf.format(Date())
        ).apply { authViewModel.getSession { this.user_id = it?.id ?: "" } }
    }

    private fun validation(): Boolean{
        var isValid = true
        if (binding.taskEt.text.toString().isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.error_task_detail))
        }
        return isValid
    }

}