package com.example.howlong.definition.listeners

import com.example.howlong.definition.enums.DateTimeType
import java.util.*

abstract class OnDateOrTimeChangedListener {
    abstract fun onDateTimeChanged(dateTime: Calendar, dateTimeType: DateTimeType)
}