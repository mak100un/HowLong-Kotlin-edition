package com.example.howlong.definition.adapters

import android.content.Context
import android.view.View
import com.example.howlong.R
import com.example.howlong.definition.adapters.base.list.BaseListArrayAdapter
import com.example.howlong.definition.enums.MenuItemType
import com.example.howlong.definition.items.MenuItem
import com.example.howlong.definition.viewholders.list.MenuItemViewHolder
import com.jakewharton.rxbinding.view.RxView
import java.util.concurrent.TimeUnit


class MenuItemAdapter
(
    context: Context,
    menuItems: List<MenuItem>,
    val tapAction: (MenuItemType) -> Unit
) :
    BaseListArrayAdapter<MenuItem, MenuItemViewHolder>(context, R.layout.menu_list_item, menuItems) {

    override fun onBindItemViewHolder(context: Context, holder: MenuItemViewHolder, element: MenuItem)
    {
        holder.imageView.setImageResource(element.imageRes)
        holder.lineView.setBackgroundResource(element.colorRes)
        holder.titleView.setText(element.titleRes)

        RxView.clicks(holder.clickView)
            .throttleFirst(250, TimeUnit.MILLISECONDS)
            .subscribe { _ -> tapAction(element.menuType) }
    }

    override fun onCreateItemViewHolder(view: View): MenuItemViewHolder {
        return MenuItemViewHolder(view)
    }
}