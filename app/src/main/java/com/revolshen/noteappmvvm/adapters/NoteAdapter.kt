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

        //Set data from database in correct places
        val currentNote = notes!![holder.adapterPosition]
        holder.textViewTitle.text = currentNote.title
        holder.textViewDescription.text = currentNote.description

    }

    //Set notes and inform that data has changed
    fun setNotes(allNotes: List<Note>){
        notes = allNotes
        notifyDataSetChanged()
    }

    //Get note from given @position
    fun getNote(position: Int): Note{
        return notes!![position]
    }

}
class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var textViewTitle: TextView = itemView.title_cardView
    var textViewDescription: TextView = itemView.message_cardView
    var textViewDate: TextView = itemView.date_note
    var note: CardView = itemView.note_cardView

}