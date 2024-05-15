package com.example.booknote

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity() {
    // Declare variables
    lateinit var titleInput: EditText
    lateinit var authorInput: EditText
    lateinit var pagesInput: EditText
    lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        // Initialize UI elements by finding them in the layout
        val titleInput = findViewById<EditText>(R.id.title_input)
        val authorInput = findViewById<EditText>(R.id.author_input)
        val pagesInput = findViewById<EditText>(R.id.pages_input)
        val addButton = findViewById<Button>(R.id.add_button)

        addButton.setOnClickListener {
            // Create an instance of MyDatabaseHelper passing the context of this activity
            val myDB = MyDatabaseHelper(this@AddActivity)
            // Call the addBook method of MyDatabaseHelper passing the input values
            myDB.addBook(
                titleInput.text.toString().trim(),
                authorInput.text.toString().trim(),
                pagesInput.text.toString().trim(),
            )
        }
    }
}