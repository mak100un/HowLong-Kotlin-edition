package com.example.howlong.definition.viewholders.list

import android.view.View
import android.widget.TextView
import com.example.howlong.R
import com.example.howlong.definition.viewholders.list.base.BaseListViewHolder

class HistoryStatisticViewHolder(view: View) : BaseListViewHolder()
{
    val placeholderView: TextView = view.findViewById(R.id.records_placeholder)
    val countView: TextView = view.findViewById(R.id.records_count)
}
