package com.example.howlong.definition.adapters.base.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement

abstract class BaseRecyclerViewHolder
<
    TItem: BaseRecyclerElement,
    TItemViewHolder: RecyclerView.ViewHolder
>
    (
    val context: Context,
    protected val itemLayoutRes: Int,
    protected val elements: ArrayList<TItem>
)
    : RecyclerView.Adapter<TItemViewHolder>()
{

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : TItemViewHolder
    {
        return onCreateElementViewHolder(onCreateElementView(inflater, parent, viewType), viewType)
    }

    override fun onBindViewHolder(holder: TItemViewHolder, position: Int)
    {
        onBindItemViewHolder(context, holder, getItem(position))
    }

    override fun getItemCount() : Int
    {
        return elements.size
    }

    override fun getItemViewType(position: Int): Int {
        return itemLayoutRes
    }

    open fun onCreateElementView(inflater: LayoutInflater, parent: ViewGroup, layoutRes: Int): View
    {
        return inflater.inflate(layoutRes, parent, false)
    }


    open fun addElements(newElements: List<TItem>)
    {
        elements.addAll(newElements)
        notifyDataSetChanged()
    }

    open fun addElement(newElement: TItem)
    {
        elements.add(newElement)
        notifyItemInserted(elements.size - 1)
    }

    open fun insertItem(newElement: TItem, position: Int)
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

    protected open fun getItem(position: Int): TItem
    {
        return elements[position]
    }

    abstract fun onBindItemViewHolder(context: Context, holder: TItemViewHolder, element: TItem)

    abstract fun onCreateElementViewHolder(view: View, layoutRes: Int) : TItemViewHolder
}