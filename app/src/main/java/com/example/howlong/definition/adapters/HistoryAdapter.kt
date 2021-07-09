package com.example.howlong.definition.adapters

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.R
import com.example.howlong.definition.adapters.base.recycler.BaseGroupedHeaderedRecyclerViewAdapter
import com.example.howlong.definition.dtos.TimeRecord
import com.example.howlong.definition.enums.HistoryGroupingType
import com.example.howlong.definition.enums.MenuItemType
import com.example.howlong.definition.items.HistoryGroupItem
import com.example.howlong.definition.items.LoadingItem
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement
import com.example.howlong.definition.viewholders.recycler.HistoryGroupViewHolder
import com.example.howlong.definition.viewholders.recycler.LoadingViewHolder
import com.example.howlong.definition.viewholders.recycler.TimeRecordViewHolder
import com.example.howlong.utils.TimeFormatterUtils
import com.jakewharton.rxbinding.view.RxView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class HistoryAdapter
(
    context: Context,
    elements: ArrayList<BaseRecyclerElement>,
    val tapAction: (View, TimeRecord) -> Unit
) :
    BaseGroupedHeaderedRecyclerViewAdapter
    <
        TimeRecord,
        BaseRecyclerElement,
        LoadingItem,
        HistoryGroupItem,
        BaseRecyclerElement,
        TimeRecordViewHolder,
        RecyclerView.ViewHolder,
        LoadingViewHolder,
        HistoryGroupViewHolder,
        RecyclerView.ViewHolder
    >
    (
        context,
        R.layout.history_recycler_item,
        -1,
        R.layout.history_recycler_footer,
        R.layout.history_recycler_records_group_item,
        -1,
        elements
    )
{
    private val headerDateFormatter = SimpleDateFormat(", yyyy", Locale("ru"))
    private val timeFormatter =  SimpleDateFormat("HH:mm", Locale("ru"))
    private val dateFormatter =  SimpleDateFormat("d MMMM, ", Locale("ru"))

    override fun onBindGroupFooterViewHolder(
        context: Context,
        holder: RecyclerView.ViewHolder,
        element: BaseRecyclerElement
    ) {
        throw NotImplementedError("No need in group footer")
    }


    override fun onBindGroupHeaderViewHolder(
        context: Context,
        holder: HistoryGroupViewHolder,
        element: HistoryGroupItem
    ) {
        holder.groupHeaderTextView.text = when(element.groupingType)
        {
            HistoryGroupingType.Month -> TimeFormatterUtils.GetMonthName(element.date.get(Calendar.MONTH)) + headerDateFormatter.format(element.date.time)
            else -> TODO("Not yet implemented")
        }
    }

    override fun onBindItemViewHolder(
        context: Context,
        holder: TimeRecordViewHolder,
        element: TimeRecord
    ) {
        holder.recordResultTextView.text =  TimeFormatterUtils.ToHHmmFormat(abs(element.recycling))
        holder.recordPeriodTextView.text = timeFormatter.format(element.startDate.time) + " - " + timeFormatter.format(element.endDate.time)

        holder.recordNameTextView.text = element.name ?: dateFormatter.format(element.startDate.time) +
                TimeFormatterUtils.GetDayOfWeekShortName(element.startDate.get(Calendar.DAY_OF_WEEK))
        when(element.recycling >= 0)
        {
            true ->
            {
                holder.recordResultPlaceholderView.setText(R.string.recycling)
                holder.recordResultTextView.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            }
            else ->
            {
                holder.recordResultPlaceholderView.setText(R.string.flaw)
                holder.recordResultTextView.setTextColor(ContextCompat.getColor(context!!, R.color.colorRed))
            }
        }

        RxView.clicks(holder.itemView)
            .throttleFirst(250, TimeUnit.MILLISECONDS)
            .subscribe { tapAction(holder.itemView, element) }
    }

    override fun onBindFooterViewHolder(
        context: Context,
        holder: LoadingViewHolder,
        element: LoadingItem
    )
    {
        // No need in binding
    }

    override fun onBindHeaderViewHolder(
        context: Context,
        holder: RecyclerView.ViewHolder,
        element: BaseRecyclerElement
    ) {
        throw NotImplementedError("No need in header")
    }

    override fun onCreateElementViewHolder(view: View, layoutRes: Int): RecyclerView.ViewHolder
    {
        return when (layoutRes)
        {
            itemLayoutRes -> TimeRecordViewHolder(view)
            footerLayoutRes -> LoadingViewHolder(view)
            groupHeaderLayoutRes -> HistoryGroupViewHolder(view)
            else -> throw NotImplementedError("Use another type of adapter")
        }
    }
}