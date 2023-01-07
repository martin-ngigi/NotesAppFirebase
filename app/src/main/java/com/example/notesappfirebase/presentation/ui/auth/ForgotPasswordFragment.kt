package com.example.notesappfirebase.presentation.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.notesappfirebase.R
import com.example.notesappfirebase.databinding.FragmentForgotPasswordBinding
import com.example.notesappfirebase.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment: Fragment(R.layout.fragment_forgot_password) {

    val TAG: String = "ForgotPasswordFragment"
    lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentForgotPasswordBinding.bind(view)

        observer()
        binding.forgotPassBtn.setOnClickListener{
            if (validation()){
                val email = binding.emailEt.text.toString()
                viewModel.forgotPassword(email)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun observer(){
        viewModel.forgotPassword.observe(viewLifecycleOwner) {state ->
            when(state){
                is UiState.Loading -> {
                    binding.forgotPassBtn.text = ""
                    binding.forgotPassProgress.show()
                }
                is UiState.Failure -> {
                    binding.forgotPassBtn.text = "Send"
                    binding.forgotPassProgress.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.forgotPassBtn.text = "Send"
                    binding.forgotPassProgress.hide()
                    binding.emailEt.setText("")
                    binding.emailLabel.text = "Password rest email has been sent successfully.\nCheck your inbox.\nIf not not in inbox, check your spam "
                    binding.emailLabel.setTextColor(R.color.green)
                    toast(state.data)
                }
            }
        }
    }

    fun validation(): Boolean {
        var isValid = true

        if (binding.emailEt.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_email))
        }else{
            if (!binding.emailEt.text.toString().isValidEmail()){
                isValid = false
                toast(getString(R.string.invalid_email))
            }
        }

        return isValid
    }
}