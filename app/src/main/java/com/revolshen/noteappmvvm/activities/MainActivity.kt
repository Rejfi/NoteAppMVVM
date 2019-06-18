package com.revolshen.noteappmvvm.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.revolshen.noteappmvvm.adapters.NoteAdapter
import com.revolshen.noteappmvvm.viewmodels.NoteViewModel
import com.revolshen.noteappmvvm.R
import com.revolshen.noteappmvvm.datas.Note
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.note_cardview.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val EDIT_CODE = 1 //Request code for intent
        const val NEW_NOTE = 2
    }

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set recyclerView layoutManager & adapter
        val adapter = NoteAdapter()
        recycler_view.adapter = adapter
        recycler_view.layoutManager = GridLayoutManager(applicationContext, 2)
        recycler_view.setHasFixedSize(true)

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes().observe(this, Observer<List<Note>> {
            //Update recyclerView adapter to show current notes
            adapter.setNotes(it)
        })

        //Click FB to take new note
        newNoteBT.setOnClickListener {
            val intent = Intent(applicationContext, EditActivity::class.java)
            startActivityForResult(intent, NEW_NOTE)
        }


        //Swiping notes to delete them
        ItemTouchHelper(object : SimpleCallback(0, RIGHT or LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNote(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val intent = Intent(applicationContext, EditActivity::class.java)
                intent.putExtra(EditActivity.EXTRA_ID, note.id)
                intent.putExtra(EditActivity.EXTRA_TITLE, note.title)
                intent.putExtra(EditActivity.EXTRA_MESSAGE, note.message)

                startActivityForResult(intent, EDIT_CODE)
            }
        })


    }

    //Insert data using viewModel. Data from EditActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == NEW_NOTE && resultCode == Activity.RESULT_OK){
                val newNote = Note(
                    data!!.getStringExtra(EditActivity.EXTRA_TITLE),
                    data.getStringExtra(EditActivity.EXTRA_MESSAGE),
                    Calendar.getInstance().time.toString())
                noteViewModel.insert(newNote)
        }
        if(requestCode== EDIT_CODE && resultCode== Activity.RESULT_OK){
            val updateNote = Note(
                data!!.getStringExtra(EditActivity.EXTRA_TITLE),
                data.getStringExtra(EditActivity.EXTRA_MESSAGE),
                Calendar.getInstance().time.toString()
            )
            updateNote.id = data.getIntExtra(EditActivity.EXTRA_ID, -1)
            noteViewModel.update(updateNote)
        }

    }




}

