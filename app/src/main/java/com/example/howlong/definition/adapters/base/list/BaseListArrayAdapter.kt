package com.example.howlong.definition.adapters.base.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.howlong.definition.items.base.list.BaseListElement
import com.example.howlong.definition.viewholders.list.base.BaseListViewHolder

abstract class BaseListArrayAdapter
<
    TItem: BaseListElement,
    TItemViewHolder: BaseListViewHolder
>
(
    context: Context,
    private val itemLayoutRes: Int,
    protected val elements: List<BaseListElement>
) :  ArrayAdapter<BaseListElement>(context, itemLayoutRes, elements) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layout = convertView

        val element = elements[position] as TItem
        val holder: Any

        if (layout == null)
        {
            layout = onCreateItemView(inflater, element, parent)
            holder = onCreateItemViewHolder(layout)
            layout.tag = holder
        }
        else
        {
            holder = layout.tag
        }

        onBindItemViewHolder(context, holder as TItemViewHolder, element)

        return layout!!
    }

    open fun onCreateItemView(inflater: LayoutInflater, element: TItem, parent: ViewGroup): View
    {
        return inflater.inflate(itemLayoutRes, parent, false)
    }

    abstract fun onBindItemViewHolder(context: Context, holder: TItemViewHolder, element: TItem)

    abstract fun onCreateItemViewHolder(view: View) : Any
}