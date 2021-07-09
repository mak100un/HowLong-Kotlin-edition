package com.example.howlong.definition.viewholders.recycler

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.howlong.R

class TwoButtonsSettingViewHolder(view: View): BaseSettingViewHolder(view)
{
    val firstButton: Button = view.findViewById(R.id.setting_button_1)
    val secondButton: Button = view.findViewById(R.id.setting_button_2)
}