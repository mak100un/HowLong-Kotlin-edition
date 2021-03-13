package com.example.howlong.definition.items

import com.example.howlong.definition.enums.HistoryGroupingType
import com.example.howlong.definition.enums.RecyclerElementType
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement
import java.util.*

class HistoryGroupItem(val date: Calendar, var groupingType: HistoryGroupingType) : BaseRecyclerElement()
{
    override val recyclerElementType = RecyclerElementType.GroupHeader
}