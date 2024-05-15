package com.example.booknote

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class UpdateActivity: AppCompatActivity() {

    // Declare UI elements
    private lateinit var title_input: EditText
    private lateinit var author_input: EditText
    private lateinit var pages_input: EditText
    private lateinit var update_button: Button
    private lateinit var delete_button: Button

    private lateinit var id: String
    private lateinit var title: String
    private lateinit var author: String
    private lateinit var pages: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        // Initialize UI elements
        title_input = findViewById(R.id.title_input2)
        author_input = findViewById(R.id.author_input2)
        pages_input = findViewById(R.id.pages_input2)
        update_button = findViewById(R.id.update_button)
        delete_button = findViewById(R.id.delete_button)

        // Set actionbar title after getAndSetIntentData method
        supportActionBar?.title = title

        // Set click listener for the update button
        update_button.setOnClickListener {
            // And only then we call this
            val myDB = MyDatabaseHelper(this)

            // Get updated data from EditText fields
            title = title_input.text.toString().trim()
            author = author_input.text.toString().trim()
            pages = pages_input.text.toString().trim()


            // Call the updateData method in MyDatabaseHelper to update the database
            myDB.updateData(id, title, author, pages)
        }

        // First we call this
        getAndSetIntentData()

        delete_button.setOnClickListener {
            confirmDialog()// Show confirmation dialog before deleting the dataq
        }
    }

    private fun getAndSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("title") &&
            intent.hasExtra("author") && intent.hasExtra("pages")) {
            // Getting Data from Intent
            id = intent.getStringExtra("id").toString()
            title = intent.getStringExtra("title").toString()
            author = intent.getStringExtra("author").toString()
            pages = intent.getStringExtra("pages").toString()

            // Setting Intent Data
            title_input.setText(title)
            author_input.setText(author)
            pages_input.setText(pages)
            Log.d("stev", "$title $author $pages")
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
    }

    // Show dialog before deleting the data
    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete $title?")
        builder.setMessage("Are you sure you want to delete $title?")
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            val myDB = MyDatabaseHelper(this)
            myDB.deleteOneRow(id)
            finish()
        }
        builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
            // Do nothing
        }
        builder.create().show()
    }


}