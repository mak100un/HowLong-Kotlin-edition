package com.example.howlong.definition.interfaces

import com.example.howlong.definition.items.MenuItem

internal interface OnMenuItemClickListener {
    fun onMenuItemClick(menuItem: MenuItem?, position: Int)
}