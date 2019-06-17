package com.revolshen.noteappmvvm.datas

import android.app.Application
import androidx.lifecycle.LiveData
import android.os.AsyncTask

/*
class NoteRepository(application: Application) {

    private var noteDAO: NoteDAO
    private var notes: LiveData<List<Note>>

    init {
        val database: NoteDataBase =
            NoteDataBase.
                getInstance(application.applicationContext)!!
        noteDAO = database.noteDao()
        notes = noteDAO.getAllNotes()

    }
    fun insert(note: Note): AsyncTask<Note, Unit, Unit> =
        InsertNoteAsyncTask(noteDAO).execute(note)


    fun update(note: Note) =
        UpdateNoteAsyncTask(noteDAO).execute(note)


    fun delete(note: Note) =
        DeleteNoteAsynchTask(noteDAO).execute(note)


    fun deleteAllNotes() =
            DeleteAllNotesAsyncTask(noteDAO).execute()


    //TUTAJ WPROWADZIŁEM zmiany z return notes ---> notes
    fun getAllNotes(): LiveData<List<Note>> = notes


    companion object{

        private class InsertNoteAsyncTask(val noteDAO: NoteDAO): AsyncTask<Note, Unit, Unit>(){

            override fun doInBackground(vararg note: Note?) {
                noteDAO.insert(note[0]!!)
            }

        }

            private class UpdateNoteAsyncTask(val noteDAO: NoteDAO): AsyncTask<Note, Unit, Unit>(){
                override fun doInBackground(vararg note: Note?) {
                    noteDAO.update(note[0]!!)
                }

            }

        private class DeleteNoteAsynchTask(val noteDAO: NoteDAO): AsyncTask<Note, Unit, Unit>(){
            override fun doInBackground(vararg note: Note?) {
                noteDAO.delete(note[0]!!)
            }

        }

        private class DeleteAllNotesAsyncTask(val noteDAO: NoteDAO): AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                noteDAO.deleteAllNotes()
            }


        }

    }

}*/

class NoteRepository(application: Application) {

    private var noteDao: NoteDAO

    private var allNotes: LiveData<List<Note>>

    init {
        val database: NoteDataBase = NoteDataBase.getInstance(
            application.applicationContext
        )!!
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note: Note) {
        val insertNoteAsyncTask = InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note: Note) {
        val updateNoteAsyncTask = UpdateNoteAsyncTask(noteDao).execute(note)
    }


    fun delete(note: Note) {
        val deleteNoteAsyncTask = DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes() {
        val deleteAllNotesAsyncTask = DeleteAllNotesAsyncTask(
            noteDao
        ).execute()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }

    companion object {
        private class InsertNoteAsyncTask(val noteDao: NoteDAO) : AsyncTask<Note, Unit, Unit>() {

            override fun doInBackground(vararg p0: Note?) {
                noteDao.insert(p0[0]!!)
            }
        }

        private class UpdateNoteAsyncTask(val noteDao: NoteDAO) : AsyncTask<Note, Unit, Unit>() {

            override fun doInBackground(vararg p0: Note?) {
                noteDao.update(p0[0]!!)
            }
        }

        private class DeleteNoteAsyncTask(val noteDao: NoteDAO) : AsyncTask<Note, Unit, Unit>() {

            override fun doInBackground(vararg p0: Note?) {
                noteDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllNotesAsyncTask(val noteDao: NoteDAO) : AsyncTask<Unit, Unit, Unit>() {

            override fun doInBackground(vararg p0: Unit?) {
                noteDao.deleteAllNotes()
            }
        }
    }
}