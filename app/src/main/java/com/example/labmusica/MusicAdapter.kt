package com.example.labmusica

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class MusicAdapter(val context : Context, val items: List<Item>) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view : View = LayoutInflater.from(p0.context).inflate(R.layout.music_row, p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = items[p1]
        p0.name.text = item.name
        p0.autor.text = item.autor
        p0.img.setImageResource(item.img)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var img : ImageView = itemView.findViewById(R.id.imageView)
        var name : TextView = itemView.findViewById(R.id.textView2)
        var autor : TextView = itemView.findViewById(R.id.textView3)
    }
}