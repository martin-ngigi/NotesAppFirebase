package com.example.notesappfirebase.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.notesappfirebase.R
import com.example.notesappfirebase.databinding.FragmentHomeBinding


class HomeFragment: Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
    }
}