package com.example.howlong.definition.viewholders.recycler

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.R

class TimeRecordViewHolder(view: View): RecyclerView.ViewHolder(view)
{
    val recordNameTextView: TextView = view.findViewById(R.id.record_name)
    val recordPeriodTextView: TextView = view.findViewById(R.id.record_period)
    val resultBlockView: LinearLayout = view.findViewById(R.id.resultBlock)
    val recordResultPlaceholderView: TextView = view.findViewById(R.id.record_result_placeholder)
    val recordResultTextView: TextView = view.findViewById(R.id.record_result)
}