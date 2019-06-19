package com.revolshen.noteappmvvm.activities

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.revolshen.noteappmvvm.R
import com.revolshen.noteappmvvm.adapters.NoteAdapter
import com.revolshen.noteappmvvm.datas.Note
import com.revolshen.noteappmvvm.viewmodels.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
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

        title = "Moje notatki"

        //Set recyclerView layoutManager & adapter
        val adapter = NoteAdapter()
        recycler_view.adapter = adapter
       // recycler_view.layoutManager = GridLayoutManager(applicationContext, 2)
        recycler_view.setHasFixedSize(true)

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes().observe(this, Observer<List<Note>> {
            //Update recyclerView adapter to show current notes
            adapter.submitList(it)
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
               // adapter.notifyItemRemoved(viewHolder.adapterPosition)

            }

        }).attachToRecyclerView(recycler_view)

       //Click note to edit
        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val intent = Intent(applicationContext, EditActivity::class.java)
                intent.putExtra(EditActivity.EXTRA_ID, note.id)
                intent.putExtra(EditActivity.EXTRA_TITLE, note.title)
                intent.putExtra(EditActivity.EXTRA_MESSAGE, note.message)

                startActivityForResult(intent, EDIT_CODE)
            }
        })

        //Click long to share the note through the selected application
        adapter.setOnItemLongClickListener(object : NoteAdapter.OnItemLongClickListener{
            override fun onItemLongClick(note: Note) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, note.message)
                    type = "text/plain"
                }
                Toast.makeText(applicationContext, "Udostępnianie notatki", Toast.LENGTH_LONG).apply {
                    setGravity(Gravity.TOP,0, 0)
                    show()
                }
                startActivity(sendIntent)
            }

        })

    }
    //Insert data using viewModel. Data from EditActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Current date for note in scheme YYYY-MM-DD
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        //Check that note was creating or editing
        if(requestCode == NEW_NOTE && resultCode == Activity.RESULT_OK){

            val newNote = Note(
                    data!!.getStringExtra(EditActivity.EXTRA_TITLE),
                    data.getStringExtra(EditActivity.EXTRA_MESSAGE),
                    currentDate)
                noteViewModel.insert(newNote)
        }
        if(requestCode== EDIT_CODE && resultCode== Activity.RESULT_OK){

            val updateNote = Note(
                data!!.getStringExtra(EditActivity.EXTRA_TITLE),
                data.getStringExtra(EditActivity.EXTRA_MESSAGE),
                currentDate)
            updateNote.id = data.getIntExtra(EditActivity.EXTRA_ID, -1)
            noteViewModel.update(updateNote)
        }

    }

    override fun onStart() {
        super.onStart()

        //Set the most suitable layout for recyclerView depends on Orientation
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recycler_view.layoutManager = GridLayoutManager(applicationContext, 3)
        } else {
            recycler_view.layoutManager = GridLayoutManager(applicationContext, 2)
        }


    }


}

