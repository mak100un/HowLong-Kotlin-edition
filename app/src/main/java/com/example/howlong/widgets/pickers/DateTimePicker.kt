package com.example.howlong.widgets.pickers

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.howlong.R
import com.example.howlong.definition.enums.DateTimeType
import com.example.howlong.definition.listeners.OnDateOrTimeChangedListener
import com.example.howlong.definition.listeners.OnDateTimeChangedListener
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*
import java.util.*


class DateTimePicker: LinearLayout {
    private var validationJob: Job? = null
    private var dateTimeChangedListener: OnDateTimeChangedListener? = null
    private var datePicker: DatePickerEditText
    private var timePicker: TimePickerEditText
    private var datePickerInputLayout: TextInputLayout
    private var timePickerInputLayout: TextInputLayout
    private var dateTimeBackField: Calendar? = null
    private val dateNotSelectedError: String = resources.getString(R.string.date_not_selected)
    private val timeNotSelectedError: String = resources.getString(R.string.time_not_selected)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.date_time_picker_layout, this)
        datePicker = findViewById(R.id.date_picker)
        timePicker = findViewById(R.id.time_picker)
        datePickerInputLayout = findViewById(R.id.date_picker_input_layout)
        timePickerInputLayout = findViewById(R.id.time_picker_input_layout)
    }

    var dateTime: Calendar?
        get() {
            if (!isValid)
                return null

            return dateTimeBackField
        }
        set(value){
            datePicker.dateTime = value
            timePicker.dateTime = value
            dateTimeBackField = value
            if (value != null)
            {
                hideDatePickerError()
                hideTimePickerError()
            }
        }

    val isValid: Boolean
        get() = datePicker.dateTime != null &&
                timePicker.dateTime != null

    fun validate(): Boolean
    {
        if (isValid)
            return true

        validatePickers()
        return false
    }

    fun setOnDateTimeChangedListener(dateTimeChangedListener: OnDateTimeChangedListener?)
    {
        this.dateTimeChangedListener = dateTimeChangedListener

        if (dateTimeChangedListener != null)
        {
            val dateOrTimeChangedListener = object : OnDateOrTimeChangedListener(){
                override fun onDateTimeChanged(dateTime: Calendar, dateTimeType: DateTimeType) {
                    when (dateTimeType) {
                        DateTimeType.Date -> hideDatePickerError()
                        DateTimeType.Time -> hideTimePickerError()
                    }
                    onDateTimeChanged()
                }
            }
            timePicker.setOnDateTimeChangedListener(dateOrTimeChangedListener)
            datePicker.setOnDateTimeChangedListener(dateOrTimeChangedListener)
            return
        }

        timePicker.setOnDateTimeChangedListener(null)
        datePicker.setOnDateTimeChangedListener(null)
    }

    private fun onDateTimeChanged()
    {
        if (!isValid)
            return

        dateTimeBackField = GregorianCalendar(datePicker.dateTime!!.get(Calendar.YEAR),
            datePicker.dateTime!!.get(Calendar.MONTH),
            datePicker.dateTime!!.get(Calendar.DAY_OF_MONTH),
            timePicker.dateTime!!.get(Calendar.HOUR_OF_DAY),
            timePicker.dateTime!!.get(Calendar.MINUTE),
            timePicker.dateTime!!.get(Calendar.SECOND))

        dateTimeChangedListener?.onDateTimeChanged(dateTimeBackField!!)
    }

    private fun validatePickers() {
        validationJob?.cancel()
        validationJob = GlobalScope.launch {
            withContext(Dispatchers.Main)
            {
                if (datePicker.dateTime == null)
                {
                    datePickerInputLayout.error = dateNotSelectedError
                }

                if (timePicker.dateTime == null)
                {
                    timePickerInputLayout.error = timeNotSelectedError
                }
            }
            delay(2000)
            ensureActive()
            withContext(Dispatchers.Main)
            {

                hideDatePickerError()
                hideTimePickerError()
            }
        }
    }

    private fun hideTimePickerError() {
        timePickerInputLayout.error = null
    }

    private fun hideDatePickerError() {
        datePickerInputLayout.error = null
    }

}