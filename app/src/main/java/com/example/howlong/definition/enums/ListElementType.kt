package com.example.howlong.definition.enums

enum class ListElementType {
    Item,
    GroupHeader,
    GroupFooter;

    fun getEqualListElementType(recyclerElementType: RecyclerElementType): ListElementType{
        return when(recyclerElementType)
        {
            RecyclerElementType.Item -> Item
            RecyclerElementType.GroupFooter -> GroupFooter
            RecyclerElementType.GroupHeader -> GroupHeader
            else -> throw NotImplementedError()
        }
    }
}