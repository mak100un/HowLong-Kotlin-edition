package com.example.howlong.definition.adapters.base.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.howlong.definition.enums.ListElementType
import com.example.howlong.definition.items.base.list.BaseListElement
import com.example.howlong.definition.viewholders.list.base.BaseListViewHolder

abstract class BaseGroupedListArrayAdapter
    <
        TItem: BaseListElement,
        TGroupHeader: BaseListElement,
        TGroupFooter: BaseListElement,
        TItemViewHolder: BaseListViewHolder,
        TGroupHeaderViewHolder: BaseListViewHolder,
        TGroupFooterViewHolder: BaseListViewHolder
    >
    (
        context: Context,
        private val itemLayoutRes: Int,
        private val groupHeaderLayoutRes: Int,
        private val groupFooterLayoutRes: Int,
        protected val elements: List<BaseListElement>
    ) :  ArrayAdapter<BaseListElement>(context, itemLayoutRes, elements) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layout = convertView

        val element = elements[position]
        val holder: Any

        when (element.listElementType)
        {
            ListElementType.Item ->
            {
                val typedElement = element as TItem

                if (layout == null)
                {
                    layout = onCreateItemView(inflater, typedElement, parent)
                    holder = onCreateItemViewHolder(layout)
                    layout.tag = holder
                }
                else
                {
                    holder = layout.tag
                }

                onBindItemViewHolder(context, holder as TItemViewHolder, typedElement)
            }
            ListElementType.GroupFooter ->
            {
                val typedElement = element as TGroupFooter

                if (layout == null)
                {
                    layout = onCreateGroupFooterView(inflater, typedElement, parent)
                    holder = onCreateGroupFooterViewHolder(layout)
                    layout.tag = holder
                }
                else
                {
                    holder = layout.tag
                }

                onBindGroupFooterViewHolder(context, holder as TGroupFooterViewHolder, typedElement)
            }
            ListElementType.GroupHeader ->
            {
                val typedElement = element as TGroupHeader

                if (layout == null)
                {
                    layout = onCreateGroupHeaderView(inflater, typedElement, parent)
                    holder = onCreateGroupHeaderViewHolder(layout)
                    layout.tag = holder
                }
                else
                {
                    holder = layout.tag
                }

                onBindGroupHeaderViewHolder(context, holder as TGroupHeaderViewHolder, typedElement)
            }
        }

        return layout!!
    }

    override fun isEnabled(position: Int) : Boolean
    {
        return elements[position].listElementType == ListElementType.Item
    }

    open fun onCreateItemView(inflater: LayoutInflater, element: TItem, parent: ViewGroup): View
    {
        return inflater.inflate(itemLayoutRes, parent, false)
    }

    open fun onCreateGroupFooterView(inflater: LayoutInflater, element: TGroupFooter, parent: ViewGroup): View
    {
        return inflater.inflate(groupFooterLayoutRes, parent, false)
    }

    open fun onCreateGroupHeaderView(inflater: LayoutInflater, element: TGroupHeader, parent: ViewGroup): View
    {
        return inflater.inflate(groupHeaderLayoutRes, parent, false)
    }

    abstract fun onBindItemViewHolder(context: Context, holder: TItemViewHolder, element: TItem)
    abstract fun onBindGroupFooterViewHolder(context: Context, holder: TGroupFooterViewHolder, element: TGroupFooter)
    abstract fun onBindGroupHeaderViewHolder(context: Context, holder: TGroupHeaderViewHolder, element: TGroupHeader)

    abstract fun onCreateItemViewHolder(view: View) : Any
    abstract fun onCreateGroupFooterViewHolder(view: View) : Any
    abstract fun onCreateGroupHeaderViewHolder(view: View) : Any
}