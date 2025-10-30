package com.example.smartnote

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.smartnote.data.Note
import com.example.smartnote.databinding.ActivityAddNoteBinding
import com.example.smartnote.viewmodel.NoteViewModel

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()
            val tags = binding.etTags.text.toString()
            if (title.isNotBlank()) {
                viewModel.insert(Note(title = title, content = content, tags = tags))
                finish()
            }
        }
    }
}
