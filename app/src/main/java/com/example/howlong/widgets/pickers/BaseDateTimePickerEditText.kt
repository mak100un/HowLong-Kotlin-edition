package com.example.howlong.widgets.pickers

import android.content.Context
import android.util.AttributeSet
import com.example.howlong.definition.enums.DateTimeType
import com.example.howlong.definition.listeners.OnDateOrTimeChangedListener
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseDateTimePickerEditText: BasePickerEditText {
    private var dateOrTimeChangedListener: OnDateOrTimeChangedListener? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var dateTime: Calendar? = null
    set(value){
        if (equals(field, value))
        {
            return
        }

        field = value
        field?.let { setText(dateTimeFormatter.format(it.time)) } ?: setText("")
    }

    protected abstract val dateTimeFormatter: SimpleDateFormat
    protected abstract val dateTimeType: DateTimeType

    fun setOnDateTimeChangedListener(dateOrTimeChangedListener: OnDateOrTimeChangedListener?)
    {
        this.dateOrTimeChangedListener = dateOrTimeChangedListener
    }

    protected fun onDateTimeChanged()
    {
        dateTime?.let { dateOrTimeChangedListener?.onDateTimeChanged(it, dateTimeType) }
    }


    protected abstract fun equals(field: Calendar?, value: Calendar?): Boolean
}