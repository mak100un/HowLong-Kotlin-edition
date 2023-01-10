package com.example.howlong.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.howlong.R
import com.example.howlong.widgets.edittexts.FocusableEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit

class TimeInputView: LinearLayout {
    private var validationJob: Job? = null
    private val components: ArrayList<TimeInputComponent> = ArrayList()
    private val valueMustBeIntegerError: String = resources.getString(R.string.value_must_be_integer)
    private val valueMustBeMoreThanZero: String = resources.getString(R.string.value_must_be_more_than_zero)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.time_input_view, this)
        components.add(TimeInputComponent(findViewById(R.id.days_edit_text), findViewById(R.id.days_input_layout)) { days -> TimeUnit.DAYS.toMillis(days)})
        components.add(TimeInputComponent(findViewById(R.id.hours_edit_text), findViewById(R.id.hours_input_layout)) { hours -> TimeUnit.HOURS.toMillis(hours)})
        components.add(TimeInputComponent(findViewById(R.id.minutes_edit_text), findViewById(R.id.minutes_input_layout)) { minutes -> TimeUnit.MINUTES.toMillis(minutes)})
        components.add(TimeInputComponent(findViewById(R.id.seconds_edit_text), findViewById(R.id.seconds_input_layout)) { seconds -> TimeUnit.SECONDS.toMillis(seconds)})
    }

    val timeInMillis: Long?
        get() {
            if (!isValid)
                return null

            var timeInMillis = 0L

            for (component in components.filter { item -> !item.editText.text.isNullOrBlank() })
            {
                component.editText.text?.toString()?.toLongOrNull()?.let {
                    timeInMillis += component.calcMillis(it) }
            }

            return timeInMillis
        }

    val isValid: Boolean
        get()
        {
            var tempIsValid = true

            for (component in components.filter { item -> !item.editText.text.isNullOrBlank() })
            {
                val time = component.editText.text?.toString()?.toLongOrNull()
                tempIsValid = time != null && time >= 0

                if (!tempIsValid)
                    return tempIsValid
            }

            return tempIsValid
        }

    fun validate(): Boolean
    {
        if (isValid)
            return true

        validateEditTexts()
        return false
    }

    private fun validateEditTexts() {
        validationJob?.cancel()
        validationJob = GlobalScope.launch {
            withContext(Dispatchers.Main)
            {
                for (component in components.filter { item -> !item.editText.text.isNullOrBlank() })
                {
                    val time = component.editText.text?.toString()?.toLongOrNull()
                    if (time == null)
                        component.inputLayout.error = valueMustBeIntegerError

                    else if (time < 0)
                        component.inputLayout.error = valueMustBeMoreThanZero
                }
            }
            delay(2000)
            ensureActive()
            withContext(Dispatchers.Main)
            {

                for (component in components)
                {
                    component.inputLayout.error = null
                }
            }
        }
    }

    private class TimeInputComponent(
        val editText: FocusableEditText,
        val inputLayout: TextInputLayout,
        val calcMillis: (Long) -> Long
    )
}