package com.example.howlong.definition.listeners

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerPaginationListener
(
    protected val layoutManager: LinearLayoutManager,
    protected val loadingOffset: Int,
    val additionalOnScrolled: (RecyclerView, Int, Int) -> Unit
): RecyclerView.OnScrollListener()
{
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        additionalOnScrolled(recyclerView, dx, dy)
        val diff = layoutManager.itemCount - loadingOffset
        if (onNeedLoadMore() &&
            diff > 0 &&
            layoutManager.findLastVisibleItemPosition() >= diff)
            onLoadMore()
    }

    abstract fun onNeedLoadMore(): Boolean
    abstract fun onLoadMore()
}