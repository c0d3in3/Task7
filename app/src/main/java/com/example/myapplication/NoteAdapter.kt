package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val deleteNote: (String) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private var notes: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false))

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position]){
            deleteNote(it)
        }
    }

    fun update(notes: List<String>, position: Int? = null, noteRemoved: Boolean? = null) {
        this.notes = notes
        if (position != null) {
            if (noteRemoved == true)
                notifyItemRemoved(position)
            else
                notifyItemInserted(position)
        } else
            notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: String, deleteNote: (String) -> Unit) {
            itemView.findViewById<TextView>(R.id.note).text = note
            itemView.findViewById<TextView>(R.id.delete).setOnClickListener {
                deleteNote.invoke(note)
            }
        }
    }
}