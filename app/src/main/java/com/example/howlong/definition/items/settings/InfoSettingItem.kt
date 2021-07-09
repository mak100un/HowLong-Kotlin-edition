package com.example.howlong.definition.items.settings

import com.example.howlong.definition.enums.SettingItemType
import com.example.howlong.R

class InfoSettingItem(
    settingItemType: SettingItemType,
    val action: (() -> Unit)? = null,
    hasLine: Boolean = true
): BaseSettingItem(settingItemType, hasLine) {

    val descriptionRes: Int = when (settingItemType) {
        SettingItemType.CleanHistory -> R.string.clean_history_description
        SettingItemType.Version -> R.string.version_description
        SettingItemType.Support -> R.string.support_description
        SettingItemType.Improvement -> R.string.improvement_description
        SettingItemType.Developer -> R.string.developer_description
        else -> throw NotImplementedError("No implementation for $settingItemType")
    }

}