package com.example.howlong.definition.dtos

import com.example.howlong.definition.enums.RecyclerElementType
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement
import java.io.Serializable

class CommandDto(val name: String, val description: String): BaseRecyclerElement(), Serializable {
    override val recyclerElementType = RecyclerElementType.Item
}