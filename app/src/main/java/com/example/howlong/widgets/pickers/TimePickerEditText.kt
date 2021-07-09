package com.example.howlong.widgets.pickers

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateFormat
import android.util.AttributeSet
import com.example.howlong.definition.enums.DateTimeType
import java.text.SimpleDateFormat
import java.util.*

class TimePickerEditText:
    BaseDateTimePickerEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override val dateTimeFormatter = SimpleDateFormat("HH:mm", Locale("ru"))
    override val dateTimeType = DateTimeType.Time

    override fun equals(field: Calendar?, value: Calendar?): Boolean {
        return field?.get(Calendar.HOUR_OF_DAY) == value?.get(Calendar.HOUR_OF_DAY) &&
                field?.get(Calendar.MINUTE) == value?.get(Calendar.MINUTE) &&
                field?.get(Calendar.SECOND) == value?.get(Calendar.SECOND)
    }

    override fun onCreateDialog() : AlertDialog {
        var selectedDate =  dateTime ?:  GregorianCalendar.getInstance()
        return TimePickerDialog(context,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                selectedDate = GregorianCalendar(0, 0, 0, hourOfDay, minute)
                if (selectedDate.get(Calendar.HOUR_OF_DAY) != dateTime?.get(Calendar.HOUR_OF_DAY) ||
                    selectedDate.get(Calendar.MINUTE) != dateTime?.get(Calendar.MINUTE))
                {
                    dateTime = selectedDate
                    onDateTimeChanged()
                }
                onDismissDialog()
            }, selectedDate.get(Calendar.HOUR_OF_DAY), selectedDate.get(Calendar.MINUTE),  DateFormat.is24HourFormat(context))
    }
}