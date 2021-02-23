package com.example.howlong.definition.adapters

import com.example.howlong.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.definition.interfaces.OnMenuItemClickListener
import com.example.howlong.definition.items.MenuItem


class MenuItemAdapter internal constructor(
    context: Context?,
    menuItems: List<MenuItem>,
    private val onClickListener: OnMenuItemClickListener
) :
    RecyclerView.Adapter<MenuItemAdapter.ViewHolder>() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val menuItems: List<MenuItem> = menuItems
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.menu_list_item, parent, false))
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val menuItem: MenuItem = menuItems[position]
        holder.imageView.setImageResource(menuItem.imageRes)
        holder.lineView.setBackgroundColor(menuItem.colorRes)
        holder.titleView.setText(menuItem.titleRes)
        holder.itemView.setOnClickListener { onClickListener.onMenuItemClick(menuItem, position) }
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.menu_image) as ImageView
        val lineView: View = view.findViewById(R.id.menu_line)
        val titleView: TextView = view.findViewById(R.id.menu_title)
    }

}