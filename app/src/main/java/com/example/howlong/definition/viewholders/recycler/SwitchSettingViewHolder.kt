package com.example.howlong.definition.viewholders.recycler

import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.howlong.R

class SwitchSettingViewHolder(view: View): BaseSettingViewHolder(view)
{
    val descriptionTextView: TextView = view.findViewById(R.id.setting_description)
    val switch: SwitchCompat = view.findViewById(R.id.setting_switch)
}