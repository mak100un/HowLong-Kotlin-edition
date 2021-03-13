package com.example.howlong.definition.enums

enum class RecyclerElementType {
    Item,
    GroupHeader,
    GroupFooter,
    Header,
    Footer;

    fun getEqualRecyclerElementType(listElementType: ListElementType): RecyclerElementType{
        return when(listElementType)
        {
            ListElementType.Item -> Item
            ListElementType.GroupFooter -> GroupFooter
            ListElementType.GroupHeader -> GroupHeader
            else -> throw NotImplementedError()
        }
    }
}