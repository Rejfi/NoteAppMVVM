package com.revolshen.noteappmvvm.datas

import androidx.room.Entity
import androidx.room.PrimaryKey


//Class represent simple note

@Entity(tableName = "note_table")
data class Note(

    var title: String,
    var message: String,
    var date: String

) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}