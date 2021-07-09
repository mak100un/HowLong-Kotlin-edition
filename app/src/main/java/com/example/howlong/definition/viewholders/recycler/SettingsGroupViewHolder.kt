package com.example.howlong.definition.viewholders.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.R

class SettingsGroupViewHolder(view: View): RecyclerView.ViewHolder(view)
{
    val groupHeaderTextView: TextView = view.findViewById(R.id.settings_group_header_text)
}