package com.example.booknote

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(
    private val activity: Activity,
    private val context: Context,
    private val book_id: ArrayList<String>,
    private val book_title: ArrayList<String>,
    private val book_author: ArrayList<String>,
    private val book_pages: ArrayList<String>
) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>(){

    //indicating that it's an adapter for a
    // RecyclerView and it works with a custom ViewHolder named
    private var translate_anim: Animation? = null



    // onCreateViewHolder is called when RecyclerView needs a new ViewHolder instance.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return book_id.size // or return any other ArrayList's size
    }



    // called to display the data at the specified position.
    override fun onBindViewHolder(holder: CustomAdapter.MyViewHolder, position: Int) {

        // Set the data for the item at the specified position
        holder.book_id_txt.text = book_id[position].toString()
        holder.book_title_txt.text = book_title[position].toString()
        holder.book_author_txt.text = book_author[position].toString()
        holder.book_pages_txt.text = book_pages[position].toString()

        // Set click listener for the item
        holder.mainLayout.setOnClickListener {
            // Start UpdateActivity with details of the selected item
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", book_id[position])
            intent.putExtra("title", book_title[position])
            intent.putExtra("author", book_author[position])
            intent.putExtra("pages", book_pages[position])
            activity.startActivityForResult(intent, 1)
        }
    }


//    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//        return book_id.size
//    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ViewHolder class to hold references to the views for each item
        var book_id_txt: TextView = itemView.findViewById(R.id.book_id_txt)
        var book_title_txt: TextView = itemView.findViewById(R.id.book_title_txt)
        var book_author_txt: TextView = itemView.findViewById(R.id.book_author_txt)
        var book_pages_txt: TextView = itemView.findViewById(R.id.book_pages_txt)
        var mainLayout: LinearLayout = itemView.findViewById(R.id.mainLayout)

        init {
            // Animate Recyclerview
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim)
            mainLayout.animation = translate_anim
        }
    }

}