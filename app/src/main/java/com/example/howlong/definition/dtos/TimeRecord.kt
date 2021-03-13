package com.example.howlong.definition.dtos

import com.example.howlong.definition.enums.RecyclerElementType
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement
import java.util.*

class TimeRecord(var startDate: Calendar, var endDate: Calendar, val recycling: Long, var name: String?, var description: String?)
    : BaseRecyclerElement()
{
    constructor(startDate: Calendar, endDate: Calendar, recycling: Long) : this(startDate, endDate, recycling, null, null)
    constructor(startDate: Calendar, endDate: Calendar, recycling: Long, name: String?) : this(startDate, endDate, recycling, name, null)

    override val recyclerElementType = RecyclerElementType.Item
}