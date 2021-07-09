package com.example.howlong.definition.adapters.base.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.definition.enums.RecyclerElementType
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement

abstract class BaseHeaderedRecyclerViewAdapter
<
    TItem: BaseRecyclerElement,
    THeader: BaseRecyclerElement,
    TFooter: BaseRecyclerElement,
    TItemViewHolder: RecyclerView.ViewHolder,
    THeaderViewHolder: RecyclerView.ViewHolder,
    TFooterViewHolder: RecyclerView.ViewHolder
>
(
    val context: Context,
    protected val itemLayoutRes: Int,
    protected val headerLayoutRes: Int,
    protected val footerLayoutRes: Int,
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
            RecyclerElementType.Footer -> onBindFooterViewHolder(context, holder as TFooterViewHolder, element as TFooter)
            RecyclerElementType.Header -> onBindHeaderViewHolder(context, holder as THeaderViewHolder, element as THeader)
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
            RecyclerElementType.Footer -> footerLayoutRes
            RecyclerElementType.Header -> headerLayoutRes
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
    abstract fun onBindFooterViewHolder(context: Context, holder: TFooterViewHolder, element: TFooter)
    abstract fun onBindHeaderViewHolder(context: Context, holder: THeaderViewHolder, element: THeader)

    abstract fun onCreateElementViewHolder(view: View, layoutRes: Int) : RecyclerView.ViewHolder
}