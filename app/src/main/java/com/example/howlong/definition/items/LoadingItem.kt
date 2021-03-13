package com.example.howlong.definition.items

import com.example.howlong.definition.enums.RecyclerElementType
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement

class LoadingItem : BaseRecyclerElement()
{
    override val recyclerElementType = RecyclerElementType.Footer
}