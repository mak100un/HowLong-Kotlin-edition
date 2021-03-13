package com.example.howlong.definition.adapters

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.howlong.R
import com.example.howlong.definition.adapters.base.list.BaseListArrayAdapter
import com.example.howlong.definition.items.HistoryStatistic
import com.example.howlong.definition.viewholders.list.HistoryStatisticViewHolder

class HistoryStatisticsAdapter
(
    context: Context,
    historyStatistics: List<HistoryStatistic>
) :
BaseListArrayAdapter<HistoryStatistic, HistoryStatisticViewHolder>
(
    context,
    R.layout.history_bottom_sheet_statistics_list_item,
    historyStatistics
)
{
    override fun onBindItemViewHolder(context: Context, holder: HistoryStatisticViewHolder, element: HistoryStatistic)
    {
        holder.placeholderView.setText(element.placeholderTextRes)
        holder.countView.setTextColor(ContextCompat.getColor(context, element.placeholderColorRes))
        holder.countView.text = element.records_count.toString()
    }

    override fun onCreateItemViewHolder(view: View): HistoryStatisticViewHolder {
        return HistoryStatisticViewHolder(view)
    }
}