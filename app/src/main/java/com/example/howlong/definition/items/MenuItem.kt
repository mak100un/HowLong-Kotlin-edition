package com.example.howlong.definition.items

import com.example.howlong.R
import com.example.howlong.definition.enums.ListElementType
import com.example.howlong.definition.enums.MenuItemType
import com.example.howlong.definition.items.base.list.BaseListElement

class MenuItem(val menuType: MenuItemType) : BaseListElement()
{
    val titleRes: Int
    val imageRes: Int
    val colorRes: Int

    init{
        when (menuType)
        {
            MenuItemType.ActiveRecord ->
            {
                titleRes = R.string.active_record
                imageRes = R.drawable.img_active_record
                colorRes = R.color.colorActiveRecord
            }
            MenuItemType.History ->
            {
                titleRes = R.string.history
                imageRes = R.drawable.img_history
                colorRes = R.color.colorHistory
            }
            MenuItemType.Settings ->
            {
                titleRes = R.string.settings
                imageRes = R.drawable.img_settings
                colorRes = R.color.colorSettings
            }
        }
    }

    override val listElementType = ListElementType.Item
}