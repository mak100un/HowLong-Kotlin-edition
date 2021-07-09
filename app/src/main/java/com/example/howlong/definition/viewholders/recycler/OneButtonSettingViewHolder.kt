package com.example.howlong.definition.viewholders.recycler

import android.view.View
import android.widget.ImageButton
import com.example.howlong.R

open class OneButtonSettingViewHolder(view: View): BaseSettingViewHolder(view)
{
    val button: ImageButton = view.findViewById(R.id.setting_button)
}