package com.example.howlong.definition.viewholders.recycler

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.R

class LoadingViewHolder(view: View): RecyclerView.ViewHolder(view)
{
    val loadingView: ProgressBar = view.findViewById(R.id.loading_view)
}