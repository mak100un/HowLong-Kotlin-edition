package com.example.howlong.definition.items

import com.example.howlong.R
import com.example.howlong.definition.enums.HistoryStatisticType
import com.example.howlong.definition.enums.ListElementType
import com.example.howlong.definition.items.base.list.BaseListElement

class HistoryStatistic(val statisticType: HistoryStatisticType, var recordsCount: Int)
    : BaseListElement()
{
    val placeholderTextRes: Int = when (statisticType) {
        HistoryStatisticType.Recycling -> R.string.recyclings
        HistoryStatisticType.Flaw -> R.string.days_off
        HistoryStatisticType.All -> R.string.all_records
    }

    val placeholderColorRes: Int = when (statisticType) {
        HistoryStatisticType.Recycling -> R.color.colorGreen
        HistoryStatisticType.Flaw -> R.color.colorRed
        HistoryStatisticType.All -> R.color.colorPrimary
    }

    override val listElementType = ListElementType.Item
}