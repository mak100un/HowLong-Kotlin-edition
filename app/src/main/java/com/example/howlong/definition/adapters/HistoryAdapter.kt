package com.example.howlong.definition.adapters

import android.content.Context
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.R
import com.example.howlong.definition.adapters.base.recycler.BaseGroupedHeaderedRecyclerViewAdapter
import com.example.howlong.definition.dtos.TimeRecord
import com.example.howlong.definition.enums.HistoryGroupingType
import com.example.howlong.definition.items.HistoryGroupItem
import com.example.howlong.definition.items.LoadingItem
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement
import com.example.howlong.definition.viewholders.recycler.HistoryGroupViewHolder
import com.example.howlong.definition.viewholders.recycler.LoadingViewHolder
import com.example.howlong.definition.viewholders.recycler.TimeRecordViewHolder
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class HistoryAdapter
(
    context: Context,
    elements: ArrayList<BaseRecyclerElement>
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
            HistoryGroupingType.Month -> getMonthName(element.date.get(Calendar.MONTH)) + SimpleDateFormat(", yyyy", Locale("ru")).format(element.date.time)
            else -> TODO("Not yet implemented")
        }
    }

    override fun onBindItemViewHolder(
        context: Context,
        holder: TimeRecordViewHolder,
        element: TimeRecord
    ) {
        val dateTimeFormatter =  SimpleDateFormat("HH:mm", Locale("ru"))

        val timeInMillis = abs(element.recycling)
        val hours = TimeUnit.MILLISECONDS.toHours(timeInMillis)

        holder.recordResultView.text = dateTimeFormatter
            .format(GregorianCalendar(0, 0, 0, hours.toInt(), (TimeUnit.MILLISECONDS.toMinutes(timeInMillis) - hours * 60).toInt()).time)
        holder.recordPeriodView.text = dateTimeFormatter.format(element.startDate.time) + " - " + SimpleDateFormat("HH:mm", Locale("ru")).format(element.endDate.time)
        dateTimeFormatter.applyPattern("d MMMM, ")

        holder.recordNameView.text = element.name ?: dateTimeFormatter.format(element.startDate.time) + getDayOfWeekShortName(element.startDate.get(Calendar.DAY_OF_WEEK))
        when(element.recycling >= 0)
        {
            true ->
            {
                holder.recordResultPlaceholderView.setText(R.string.recycling)
                holder.recordResultView.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            }
            else ->
            {
                holder.recordResultPlaceholderView.setText(R.string.flaw)
                holder.recordResultView.setTextColor(ContextCompat.getColor(context!!, R.color.colorRed))
            }
        }
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

    private fun getMonthName(month: Int): String
    {
        return when(month)
        {
            0 -> "Январь"
            1 -> "Февраль"
            2 -> "Март"
            3 -> "Апрель"
            4 -> "Май"
            5 -> "Июнь"
            6 -> "Июль"
            7 -> "Август"
            8 -> "Сентябрь"
            9 -> "Октябрь"
            10 -> "Ноябрь"
            11 -> "Декабрь"
            else -> throw NotImplementedError("No such month")
        }
    }

    private fun getDayOfWeekShortName(dayOfWeek: Int): String
    {
        return when(dayOfWeek)
        {
            1 -> "Вс"
            2 -> "Пн"
            3 -> "Вт"
            4 -> "Ср"
            5 -> "Чт"
            6 -> "Пт"
            7 -> "Сб"
            else -> throw NotImplementedError("No such month")
        }
    }
}