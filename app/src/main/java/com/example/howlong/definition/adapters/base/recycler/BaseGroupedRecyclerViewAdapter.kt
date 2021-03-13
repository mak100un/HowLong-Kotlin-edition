package com.example.howlong.definition.adapters.base.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.definition.enums.RecyclerElementType
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement

abstract class BaseGroupedRecyclerViewAdapter
<
        TItem: BaseRecyclerElement,
        TGroupHeader: BaseRecyclerElement,
        TGroupFooter: BaseRecyclerElement,
        TItemViewHolder: RecyclerView.ViewHolder,
        TGroupHeaderViewHolder: RecyclerView.ViewHolder,
        TGroupFooterViewHolder: RecyclerView.ViewHolder
>
(
    val context: Context,
    protected val itemLayoutRes: Int,
    protected val groupHeaderLayoutRes: Int,
    protected val groupFooterLayoutRes: Int,
    protected val elements: ArrayList<BaseRecyclerElement>
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder
    {
        return onCreateElementViewHolder(onCreateElementView(inflater, parent, viewType), viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val element = getItem(position)

        when (element.recyclerElementType)
        {
            RecyclerElementType.Item -> onBindItemViewHolder(context, holder as TItemViewHolder, element as TItem)
            RecyclerElementType.GroupFooter -> onBindGroupFooterViewHolder(context, holder as TGroupFooterViewHolder, element as TGroupFooter)
            RecyclerElementType.GroupHeader -> onBindGroupHeaderViewHolder(context, holder as TGroupHeaderViewHolder, element as TGroupHeader)
            else -> throw NotImplementedError("Use another type of adapter")
        }
    }

    override fun getItemCount() : Int
    {
        return elements.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).recyclerElementType)
        {
            RecyclerElementType.Item -> itemLayoutRes
            RecyclerElementType.GroupFooter -> groupFooterLayoutRes
            RecyclerElementType.GroupHeader -> groupHeaderLayoutRes
            else -> throw NotImplementedError("Use another type of adapter")
        }
    }

    open fun onCreateElementView(inflater: LayoutInflater, parent: ViewGroup, layoutRes: Int): View
    {
        return inflater.inflate(layoutRes, parent, false)
    }

    open fun addElements(newElements: List<BaseRecyclerElement>)
    {
        elements.addAll(newElements)
        notifyDataSetChanged()
    }

    open fun addElement(newElement: BaseRecyclerElement)
    {
        elements.add(newElement)
        notifyItemInserted(elements.size - 1)
    }

    open fun insertItem(newElement: BaseRecyclerElement, position: Int)
    {
        elements.add(position, newElement)
        notifyItemInserted(position)
    }

    open fun removeItem(position: Int)
    {
        elements.remove(getItem(position))
        notifyItemRemoved(position)
    }

    open fun clear()
    {
        elements.clear()
        notifyDataSetChanged()
    }

    protected open fun getItem(position: Int): BaseRecyclerElement
    {
        return elements[position]
    }

    abstract fun onBindItemViewHolder(context: Context, holder: TItemViewHolder, element: TItem)
    abstract fun onBindGroupFooterViewHolder(context: Context, holder: TGroupFooterViewHolder, element: TGroupFooter)
    abstract fun onBindGroupHeaderViewHolder(context: Context, holder: TGroupHeaderViewHolder, element: TGroupHeader)

    abstract fun onCreateElementViewHolder(view: View, layoutRes: Int) : RecyclerView.ViewHolder
}