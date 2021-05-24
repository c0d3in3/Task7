package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.SharedPreferencesManager.Companion.NOTES
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NoteAdapter
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var note: Note

    private val onNoteDeleted: (String) -> Unit = {
        val position = note.notes.indexOf(it)
        note.notes.remove(it)
        adapter.update(
            notes = note.notes.toList(),
            noteRemoved = true,
            position = position
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferencesManager = SharedPreferencesManager(baseContext)
        note =
            Gson().fromJson<Note>(sharedPreferencesManager.readString(NOTES), Note::class.java)
                ?: Note(mutableSetOf())
        adapter = NoteAdapter(onNoteDeleted)
        recyclerView.adapter = adapter
        adapter.update(note.notes.toList())
        button.setOnClickListener { addNote() }
    }

    private fun addNote() {
        val input = editTextNote.text.toString()
        if (input.isNotEmpty()) {
            if (note.notes.add(input)) {
                adapter.update(
                    notes = note.notes.toList(),
                    noteRemoved = false,
                    position = note.notes.size - 1
                )
                editTextNote.setText("")
            } else Toast.makeText(baseContext, "This note exists already", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onPause() {
        sharedPreferencesManager.writeString(NOTES, Gson().toJson(note))
        super.onPause()
    }
}