package com.revolshen.noteappmvvm.datas

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Note::class], version = 4)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDAO

    companion object {
        private var instance: NoteDataBase? = null
        //Give Instance (Singleton) of NoteDatabase
        fun getInstance(context: Context): NoteDataBase? {
            if (instance == null) {
                synchronized(NoteDataBase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDataBase::class.java, "note_database"
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                       // .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        //Delete Singleton NoteDatabase
        fun destroyInstance() {
            instance = null
        }

    }

}