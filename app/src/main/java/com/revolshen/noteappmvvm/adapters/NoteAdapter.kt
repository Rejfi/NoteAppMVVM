package com.revolshen.noteappmvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.revolshen.noteappmvvm.datas.Note
import com.revolshen.noteappmvvm.R
import kotlinx.android.synthetic.main.note_cardview.view.*

/*
class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.NoteHolder>(){
    private var notes: List<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return NoteHolder(layoutInflater.inflate(R.layout.note_cardview, parent, false))

    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        var currentNote = notes[holder.adapterPosition]
        holder.title.text = currentNote.title
        holder.message.text = currentNote.description
    }

    class NoteHolder(view: View): RecyclerView.ViewHolder(view){
      val title: TextView = view.findViewById(R.id.title_cardView)
      val message: TextView = view.findViewById(R.id.message_cardView)
      val date: TextView = view.findViewById(R.id.date_note)
     // val priority: Int? = null
    }

    fun setNotes(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }
}*/

class NoteAdapter : RecyclerView.Adapter<NoteHolder>() {
     private var notes: List<Note>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NoteHolder(layoutInflater.inflate(R.layout.note_cardview, parent, false))

    }

    override fun getItemCount(): Int {

        return if(notes?.size==null) 0
        else notes!!.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = notes!![holder.adapterPosition]
        holder.textViewTitle.text = currentNote.title
        holder.textViewDescription.text = currentNote.description

    }

    fun setNotes(allNotes: List<Note>){
        notes = allNotes
        notifyDataSetChanged()
    }
}
class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var textViewTitle: TextView = itemView.title_cardView
    var textViewDescription: TextView = itemView.message_cardView
    var textViewDate: TextView = itemView.date_note
    var note: CardView = itemView.note_cardView

}