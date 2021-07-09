package com.example.howlong.definition.viewholders.recycler

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.howlong.R

class InfoSettingViewHolder(view: View): BaseSettingViewHolder(view)
{
    val descriptionTextView: TextView = view.findViewById(R.id.setting_description)
    val containerLinearLayout: LinearLayout = view.findViewById(R.id.setting_container)
}