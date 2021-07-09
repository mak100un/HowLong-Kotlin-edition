package com.example.howlong.definition.items.settings

import com.example.howlong.definition.enums.RecyclerElementType
import com.example.howlong.definition.enums.SettingsGroupingType
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement
import com.example.howlong.R

class SettingsGroupItem(var groupingType: SettingsGroupingType) : BaseRecyclerElement()
{
    val titleRes: Int = when (groupingType) {
        SettingsGroupingType.Main -> R.string.main
        SettingsGroupingType.Other -> R.string.other
        SettingsGroupingType.About -> R.string.about
        else -> throw NotImplementedError("No implementation for $groupingType")
    }

    override val recyclerElementType = RecyclerElementType.GroupHeader
}