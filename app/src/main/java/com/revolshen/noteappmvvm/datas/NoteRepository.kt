package com.revolshen.noteappmvvm.datas

import android.app.Application
import androidx.lifecycle.LiveData
import android.os.AsyncTask


class NoteRepository(application: Application) {

    private var noteDao: NoteDAO

    private var allNotes: LiveData<List<Note>>

    //Get database instance
    init {
        val database: NoteDataBase = NoteDataBase.getInstance(
            application.applicationContext
        )!!
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note: Note){
        InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note: Note){
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun delete(note: Note){
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes(){
        DeleteAllNotesAsyncTask(noteDao).execute()
    }

    fun getAllNotes(): LiveData<List<Note>>{return allNotes }


    //Use Database in Background
    companion object {
        private class InsertNoteAsyncTask(val noteDao: NoteDAO) : AsyncTask<Note, Unit, Unit>() {
            override fun doInBackground(vararg note: Note?) {
                noteDao.insert(note[0]!!)
            }
        }

        private class UpdateNoteAsyncTask(val noteDao: NoteDAO) : AsyncTask<Note, Unit, Unit>() {
            override fun doInBackground(vararg note: Note?) {
                noteDao.update(note[0]!!)
            }
        }

        private class DeleteNoteAsyncTask(val noteDao: NoteDAO) : AsyncTask<Note, Unit, Unit>() {
            override fun doInBackground(vararg note: Note?) {
                noteDao.delete(note[0]!!)
            }
        }

        private class DeleteAllNotesAsyncTask(val noteDao: NoteDAO) : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg note: Unit?) {
                noteDao.deleteAllNotes()
            }
        }
    }
}