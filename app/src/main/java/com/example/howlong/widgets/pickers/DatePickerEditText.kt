package com.example.howlong.widgets.pickers

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import java.util.*
import android.app.DatePickerDialog
import com.example.howlong.definition.enums.DateTimeType
import java.text.SimpleDateFormat

class DatePickerEditText:
    BaseDateTimePickerEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override val dateTimeFormatter = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))
    override val dateTimeType = DateTimeType.Date

    override fun equals(field: Calendar?, value: Calendar?): Boolean {
        return field?.get(Calendar.YEAR) == value?.get(Calendar.YEAR) &&
                field?.get(Calendar.MONTH) == value?.get(Calendar.MONTH) &&
                field?.get(Calendar.DAY_OF_MONTH) == value?.get(Calendar.DAY_OF_MONTH)
    }

    override fun onCreateDialog() : AlertDialog {
        var selectedDate = dateTime ?: GregorianCalendar.getInstance()
        return DatePickerDialog(context,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                selectedDate = GregorianCalendar(year, monthOfYear, dayOfMonth)
                if (!equals(dateTime, selectedDate))
                {
                    dateTime = selectedDate
                    onDateTimeChanged()
                }
                onDismissDialog()
            }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH))
    }
}