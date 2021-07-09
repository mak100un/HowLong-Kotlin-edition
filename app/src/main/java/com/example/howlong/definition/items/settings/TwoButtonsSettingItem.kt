package com.example.howlong.definition.items.settings

import com.example.howlong.definition.enums.SettingItemType

class TwoButtonsSettingItem
(
    settingItemType: SettingItemType,
    val firstButtonAction: () -> Unit,
    val firstButtonTextRes: Int,
    val secondButtonAction: () -> Unit,
    val secondButtonTextRes: Int,
    hasLine: Boolean = true
): BaseSettingItem(settingItemType, hasLine) {
}