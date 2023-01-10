package com.example.howlong.definition.viewholders.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.howlong.R
import com.example.howlong.definition.viewholders.list.base.BaseListViewHolder

class MenuItemViewHolder(view: View) : BaseListViewHolder()
{
    val imageView: ImageView = view.findViewById(R.id.menu_image)
    val lineView: View = view.findViewById(R.id.menu_line)
    val titleView: TextView = view.findViewById(R.id.menu_title)
    val clickView: View = view.findViewById(R.id.menu_item_click_view)
}