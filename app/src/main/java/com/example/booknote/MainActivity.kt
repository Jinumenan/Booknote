package com.example.booknote

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var add_button: FloatingActionButton
    private lateinit var empty_imageview: ImageView
    private lateinit var no_data: TextView

    // DatabaseHelper and data arrays
    private lateinit var myDB: MyDatabaseHelper
    private lateinit var book_id: ArrayList<String>
    private lateinit var book_title: ArrayList<String>
    private lateinit var book_author: ArrayList<String>
    private lateinit var book_pages: ArrayList<String>

    // Adapter for RecyclerView
    private lateinit var customAdapter: CustomAdapter


    // Called when the activity is first created
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()// Function call to enable edge-to-edge display
        setContentView(R.layout.activity_main)

// Apply window insets to adjust padding to accommodate system bars
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }

        // Initialize UI elements
    recyclerView = findViewById(R.id.recyclerview)
    add_button = findViewById(R.id.add_button)
    empty_imageview = findViewById(R.id.empty_imageView)
    no_data = findViewById(R.id.no_data)

        //navigate to AddActivity
        add_button.setOnClickListener{
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }

        // Initialize DatabaseHelper and data arrays
        myDB = MyDatabaseHelper(this)
        book_id = ArrayList()
        book_title = ArrayList()
        book_author = ArrayList()
        book_pages = ArrayList()

    // Populate data arrays with data from the database
    storeDataInArrays()

        // Initialize and set up the custom adapter for the RecyclerView
        customAdapter = CustomAdapter(this,this, book_id, book_title, book_author , book_pages)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // If the resultCode is 0 (indicating data modification), recreate the activity to refresh the data
        if (resultCode == 0) {
            recreate()
        }
    }

    //items for use in the action bar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Handle action bar item clicks here
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all) {
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    // Retrieve data from the database and populate the data arrays
    private fun storeDataInArrays() {
        val cursor: Cursor = myDB.readAllData() ?: return
        if (cursor.count == 0) {
            // If there is no data in the database, show appropriate views
            empty_imageview.visibility = View.VISIBLE
            no_data.visibility = View.VISIBLE
        } else {

            // If there is data in the database, populate the arrays with the retrieved data
            while (cursor.moveToNext()) {
                book_id.add(cursor.getString(0))
                book_title.add(cursor.getString(1))
                book_author.add(cursor.getString(2))
                book_pages.add(cursor.getString(3))
            }
            // Hide empty data views
            empty_imageview.visibility = View.GONE
            no_data.visibility = View.GONE
        }
    }

    // Show a confirmation dialog before deleting all data from the database
    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure you want to delete all Data?")
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            val myDB = MyDatabaseHelper(this)
            myDB.deleteAllData()
            // Refresh activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("No") { dialogInterface, _ -> }
        builder.create().show()
    }


}