package com.example.notesappfirebase.presentation.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesappfirebase.R
import com.example.notesappfirebase.data.models.Note
import com.example.notesappfirebase.databinding.FragmentNoteDetailBinding
import com.example.notesappfirebase.databinding.FragmentNoteListBinding
import com.example.notesappfirebase.presentation.ui.adapters.NoteListingAdapter
import com.example.notesappfirebase.presentation.viewmodel.NoteViewModel
import com.example.notesappfirebase.util.UiState
import com.example.notesappfirebase.util.hide
import com.example.notesappfirebase.util.show
import com.example.notesappfirebase.util.toast

import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NoteListingFragment : Fragment() {

    val TAG: String = "NoteListingFragment"
    private lateinit var binding: FragmentNoteListBinding
    private val viewModel: NoteViewModel by viewModels()

    var deletePosition: Int =-1 //if there is no position, it will be considered as -1
    var list: MutableList<Note> = arrayListOf()

    private val adapter by lazy {
        NoteListingAdapter(
            onItemClicked = { pos, item ->
                findNavController().navigate(R.id.action_noteListFragment_to_noteDetailFragment3, Bundle().apply {
                    putString("type", "view") //passing data between fragments
                    putParcelable("note", item)
                })
            },
            onEditClicked = { pos, item ->
                findNavController().navigate(R.id.action_noteListFragment_to_noteDetailFragment3, Bundle().apply {
                    putString("type", "edit") //passing data between fragments
                    putParcelable("note", item)
                })
            },
            onDeleteClicked = { pos, item ->
                deletePosition = pos
                viewModel.deleteNote(item)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_noteListFragment_to_noteDetailFragment3, Bundle().apply {
                putString("type", "create") //passing data between fragments
            })
        }

        //get notes
        viewModel.getNotes()
        viewModel.note.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    list = state.data.toMutableList()
                    adapter.updateList(list)
                }
            }
        }

        //delete note
        viewModel.deleteNote.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    toast(state.data)
                    if (deletePosition != -1){
                        list.removeAt(deletePosition)
                        adapter.updateList(list)
                    }

                }
            }
        }
    }
}