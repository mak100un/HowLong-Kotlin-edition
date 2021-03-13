package com.example.howlong.definition.viewholders.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.R

class HistoryGroupViewHolder(view: View): RecyclerView.ViewHolder(view)
{
    val groupHeaderTextView: TextView = view.findViewById(R.id.group_header_text)
}