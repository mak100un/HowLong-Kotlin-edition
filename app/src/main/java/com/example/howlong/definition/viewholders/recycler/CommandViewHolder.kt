package com.example.howlong.definition.viewholders.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.R

class CommandViewHolder(view: View): RecyclerView.ViewHolder(view)
{
    val commandTextView: TextView = view.findViewById(R.id.command_text_view)
}