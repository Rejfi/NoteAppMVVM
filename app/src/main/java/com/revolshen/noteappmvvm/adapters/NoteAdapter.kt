package com.revolshen.noteappmvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.revolshen.noteappmvvm.R
import com.revolshen.noteappmvvm.datas.Note
import kotlinx.android.synthetic.main.note_cardview.view.*


class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {


    private var notes: List<Note>? = null
    private var listener: OnItemClickListener? = null
    private var longClickListener: OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NoteHolder(layoutInflater.inflate(R.layout.note_cardview, parent, false))
    }

    override fun getItemCount(): Int {

        return if (notes?.size == null) 0
        else notes!!.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        //Set data from database in correct places
        val currentNote = notes!![holder.adapterPosition]

        holder.textViewTitle.text = currentNote.title
        holder.textViewDescription.text = currentNote.message
        holder.textViewDate.text = currentNote.date

    }

    //Set notes and inform that data has changed
    fun setNotes(allNotes: List<Note>) {
        notes = allNotes
        notifyDataSetChanged()
    }

    //Get note from given @position
    fun getNote(position: Int): Note {
        return notes!![position]
    }


    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnLongClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION){
                    longClickListener?.onItemLongClick(notes!![adapterPosition])
                }
                true
            }

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(notes!![adapterPosition])
                }

            }
        }

        var textViewTitle: TextView = itemView.title_cardView
        var textViewDescription: TextView = itemView.message_cardView
        var textViewDate = itemView.date_note
    }


    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(note: Note)
    }

    fun setOnItemLongClickListener(longClickListener: OnItemLongClickListener){
        this.longClickListener = longClickListener
    }
}
