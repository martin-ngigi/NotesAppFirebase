package com.example.notesappfirebase.presentation.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesappfirebase.R
import com.example.notesappfirebase.databinding.FragmentHomeBinding
import com.example.notesappfirebase.presentation.ui.auth.AuthViewModel
import com.example.notesappfirebase.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding

    val TAG: String = "HomeFragment"
    private val authViewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.logout.setOnClickListener{
            //toast("Hey there")
            binding.logout.setOnClickListener {
                authViewModel.logout {
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                }
            }
        }
    }
}