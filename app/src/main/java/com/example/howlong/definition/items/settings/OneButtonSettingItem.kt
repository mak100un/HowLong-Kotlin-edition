package com.example.howlong.definition.items.settings

import com.example.howlong.definition.enums.SettingItemType

class OneButtonSettingItem(
    settingItemType: SettingItemType,
    val action: () -> Unit,
    var buttonBackground: Int,
    hasLine: Boolean = true
): BaseSettingItem(settingItemType, hasLine) {
}