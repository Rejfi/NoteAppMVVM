package com.revolshen.noteappmvvm.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.revolshen.noteappmvvm.R
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_ID = "com.revolshen.noteappmvvm.activities.EXTRA_ID"
        const val EXTRA_TITLE = "com.revolshen.noteappmvvm.activities.EXTRA_TITLE" //Name of extras from result
        const val EXTRA_MESSAGE = "com.revolshen.noteappmvvm.activities.EXTRA_MESSAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //If data from intent exists set them in correct places
        if(intent.hasExtra(MainActivity.EDIT_CODE.toString())){
            title_detail.setText(intent.getStringExtra("title"))
            message_detail.setText(intent.getStringExtra("message"))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Save data from editText pack them and sent to MainActivity to save
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.saveBT -> {
                val intent = Intent().apply {
                    putExtra(EXTRA_TITLE, title_detail.text.toString())
                    putExtra(EXTRA_MESSAGE, message_detail.text.toString()) }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
