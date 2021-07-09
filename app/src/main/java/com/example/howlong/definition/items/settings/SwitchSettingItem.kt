package com.example.howlong.definition.items.settings

import com.example.howlong.definition.enums.SettingItemType
import com.example.howlong.R

class SwitchSettingItem
(
    settingItemType: SettingItemType,
    var isChecked: Boolean,
    hasLine: Boolean = true
): BaseSettingItem(settingItemType, hasLine)
{
    val descriptionRes: Int = when (settingItemType) {
        SettingItemType.Notifications -> R.string.notifications_description
        else -> throw NotImplementedError("No implementation for $settingItemType")
    }
}