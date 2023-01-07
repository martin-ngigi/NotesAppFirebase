package com.example.notesappfirebase.presentation.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notesappfirebase.presentation.ui.note.NoteListingFragment
import com.example.notesappfirebase.util.HomeTabs

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = HomeTabs.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            HomeTabs.NOTES.index -> NoteListingFragment.newInstance(HomeTabs.NOTES.name)
            else -> throw IllegalStateException("Fragment not found")
        }
    }
}