package com.example.meowmory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomintro.Item

class HighscoreRecyclerAdapter(val context: Context, var highscoreList: List<Item>) : RecyclerView.Adapter<HighscoreRecyclerAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int {
        return highscoreList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.highscore_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var highscoreItem = highscoreList[position]

        holder.highscoreTime.text = highscoreItem.time.toString()
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val highscoreTime = itemView.findViewById<TextView>(R.id.highscoreTime)
    }
}