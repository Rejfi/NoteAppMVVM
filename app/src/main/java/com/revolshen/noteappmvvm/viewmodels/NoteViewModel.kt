package com.revolshen.noteappmvvm.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.revolshen.noteappmvvm.datas.Note
import com.revolshen.noteappmvvm.datas.NoteRepository


class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: NoteRepository =
        NoteRepository(application)
    private var allNotes: LiveData<List<Note>> =
        repository.getAllNotes()

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun update(note: Note) {
        repository.update(note)
    }

    fun delete(note: Note) {
        repository.delete(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
}