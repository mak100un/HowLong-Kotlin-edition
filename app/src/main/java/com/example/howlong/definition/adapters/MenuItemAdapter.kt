package com.example.howlong.definition.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.howlong.R
import com.example.howlong.definition.enums.MenuItemType
import com.example.howlong.definition.items.MenuItem
import com.jakewharton.rxbinding.view.RxView
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class MenuItemAdapter(
    context: Context,
    menuItems: List<MenuItem>,
    val tapAction: (MenuItemType) -> Unit
) :
    ArrayAdapter<MenuItem>(context, R.layout.menu_list_item, menuItems) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val menuItems: List<MenuItem> = menuItems


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: ViewHolder
        var layout = convertView
        if (layout == null)
        {
            layout = inflater.inflate(R.layout.menu_list_item, parent, false)
            holder = ViewHolder(layout)
            layout.tag = holder
        }
        else
        {
            holder = layout.tag as ViewHolder
        }

        val menuItem: MenuItem = menuItems[position]
        holder.imageView.setImageResource(menuItem.imageRes)
        holder.lineView.setBackgroundResource(menuItem.colorRes)
        holder.titleView.setText(menuItem.titleRes)

        RxView.clicks(holder.clickView)
            .throttleFirst(250, TimeUnit.MILLISECONDS)
            .subscribe { _ -> tapAction(menuItem.menuType) }

        return layout!!
    }

    class ViewHolder(view: View)
    {
        val imageView: ImageView = view.findViewById(R.id.menu_image) as ImageView
        val lineView: View = view.findViewById(R.id.menu_line)
        val titleView: TextView = view.findViewById(R.id.menu_title)
        val clickView: View = view.findViewById(R.id.menu_item_click_view)
    }
}