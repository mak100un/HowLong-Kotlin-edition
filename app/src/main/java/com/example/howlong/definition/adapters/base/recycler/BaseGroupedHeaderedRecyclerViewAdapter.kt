package com.example.howlong.definition.adapters.base.recycler

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.definition.enums.RecyclerElementType
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement

abstract class BaseGroupedHeaderedRecyclerViewAdapter
<
    TItem: BaseRecyclerElement,
    THeader: BaseRecyclerElement,
    TFooter: BaseRecyclerElement,
    TGroupHeader: BaseRecyclerElement,
    TGroupFooter: BaseRecyclerElement,
    TItemViewHolder: RecyclerView.ViewHolder,
    THeaderViewHolder: RecyclerView.ViewHolder,
    TFooterViewHolder: RecyclerView.ViewHolder,
    TGroupHeaderViewHolder: RecyclerView.ViewHolder,
    TGroupFooterViewHolder: RecyclerView.ViewHolder
>
(
    context: Context,
    itemLayoutRes: Int,
    headerLayoutRes: Int,
    footerLayoutRes: Int,
    protected val groupHeaderLayoutRes: Int,
    protected val groupFooterLayoutRes: Int,
    elements: ArrayList<BaseRecyclerElement>
)
    : BaseHeaderedRecyclerViewAdapter
<
        TItem,
        THeader,
        TFooter,
        TItemViewHolder,
        THeaderViewHolder,
        TFooterViewHolder
>
(
    context,
    itemLayoutRes,
    headerLayoutRes,
    footerLayoutRes,
    elements
)
{
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val element = getItem(position)

        when (element.recyclerElementType)
        {
            RecyclerElementType.Item -> onBindItemViewHolder(context, holder as TItemViewHolder, element as TItem)
            RecyclerElementType.Footer -> onBindFooterViewHolder(context, holder as TFooterViewHolder, element as TFooter)
            RecyclerElementType.Header -> onBindHeaderViewHolder(context, holder as THeaderViewHolder, element as THeader)
            RecyclerElementType.GroupFooter -> onBindGroupFooterViewHolder(context, holder as TGroupFooterViewHolder, element as TGroupFooter)
            RecyclerElementType.GroupHeader -> onBindGroupHeaderViewHolder(context, holder as TGroupHeaderViewHolder, element as TGroupHeader)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).recyclerElementType)
        {
            RecyclerElementType.Item -> itemLayoutRes
            RecyclerElementType.Footer -> footerLayoutRes
            RecyclerElementType.Header -> headerLayoutRes
            RecyclerElementType.GroupFooter -> groupFooterLayoutRes
            RecyclerElementType.GroupHeader -> groupHeaderLayoutRes
        }
    }

    abstract fun onBindGroupFooterViewHolder(context: Context, holder: TGroupFooterViewHolder, element: TGroupFooter)
    abstract fun onBindGroupHeaderViewHolder(context: Context, holder: TGroupHeaderViewHolder, element: TGroupHeader)
}