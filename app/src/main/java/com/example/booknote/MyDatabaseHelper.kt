package com.example.booknote

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class MyDatabaseHelper(private val context: Context) : SQLiteOpenHelper(context,DATABASE_NAME, null, DATABASE_VERSION) {
//class to manage database creation and version management

    companion object{
        const val DATABASE_NAME = "Book.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "my_library"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TITLE = "book_title"
        private const val COLUMN_AUTHOR = "book_author"
        private const val COLUMN_PAGES = "book_pages"
    }

    // Called when the database is created for the first time
    override fun onCreate(db: SQLiteDatabase?) {
        // SQL query to create the table
        val query =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_TITLE TEXT, " +
                    "$COLUMN_AUTHOR TEXT, " +
                    "$COLUMN_PAGES INTEGER);"
        // Execute the query to create the table
        db?.execSQL(query)
    }


    // Called when the database needs to be upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")// Drop the existing table if it exists
        onCreate(db)// Create a new table

    }

    // add a book to the database
    fun addBook(title: String, author:String, pages: String){
        val db = this.writableDatabase// Get a writable database instance
        val cv = ContentValues() // Create a ContentValues object to store the values to be inserted

        // Put the book details
        cv.put(COLUMN_TITLE,title)
        cv.put(COLUMN_AUTHOR, author)
        cv.put(COLUMN_PAGES, pages)

        // Insert the cv into the database table
        val result = db.insert(TABLE_NAME, null, cv)
        // Show a toast message
        if (result == -1L) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Added Successfully! ", Toast.LENGTH_SHORT).show()
        }
    }

    // retrieve all data from the database
    fun readAllData(): Cursor? {
        val query = "SELECT * FROM $TABLE_NAME"// SQL query to select all rows from the table
        val db = this.readableDatabase  // Get a readable database instance
        var cursor: Cursor? = null
        cursor = db.rawQuery(query, null)
        return cursor
    }

    // update a specific row in the database
    fun updateData(row_id: String?, title: String?, author: String?, pages: String?) {
        val db = this.writableDatabase// Get a writable database instance

        // Create a ContentValues object to store the updated values
        val cv = ContentValues()
        // Put the updated values
        cv.put(COLUMN_TITLE, title)
        cv.put(COLUMN_AUTHOR, author)
        cv.put(COLUMN_PAGES, pages)
        // Update the row in the database based on the row_id
        val result = db.update(TABLE_NAME, cv, "$COLUMN_ID=?", arrayOf(row_id))
        if (result.toLong() == -1L) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    //delete a specific row from the database
    fun deleteOneRow(row_id: String?) {
        val db = this.writableDatabase// Get a writable database instance

        // Delete the row from the database based on the row_id
        val result = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(row_id))
        if (result == -1) {
            Toast.makeText(context, "Failed to delete.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Successfully deleted.", Toast.LENGTH_SHORT).show()
        }
    }
    //delete all data from the database
    fun deleteAllData() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
    }
}