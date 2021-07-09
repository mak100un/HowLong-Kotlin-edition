package com.example.howlong.definition.items.settings

import com.example.howlong.R
import com.example.howlong.definition.enums.RecyclerElementType
import com.example.howlong.definition.enums.SettingItemType
import com.example.howlong.definition.enums.SettingItemType.*
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement

abstract class BaseSettingItem(val settingItemType: SettingItemType, val hasLine: Boolean = true)
    :  BaseRecyclerElement()
{
    val titleRes: Int = when (settingItemType) {
        Version -> R.string.version
        Language -> R.string.language
        Theme -> R.string.theme
        Notifications -> R.string.notifications
        CleanHistory -> R.string.clean_history
        Support -> R.string.support
        Improvement -> R.string.improvement
        Developer -> R.string.developer
    }

    override val recyclerElementType = RecyclerElementType.Item
}