package com.example.howlong.definition.viewholders.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.R

abstract class BaseSettingViewHolder(view: View): RecyclerView.ViewHolder(view)
{
    val titleTextView: TextView = view.findViewById(R.id.setting_title)
    val dividerView: View = view.findViewById(R.id.setting_divider)
}