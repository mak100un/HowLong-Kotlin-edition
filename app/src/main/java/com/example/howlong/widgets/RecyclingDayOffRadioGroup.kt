package com.example.howlong.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.RadioGroup
import androidx.appcompat.widget.SwitchCompat
import com.example.howlong.R

class RecyclingDayOffRadioGroup: RadioGroup {
    private var specifyNormTimeInputView: TimeInputView
    private var specifyManuallyTimeInputView: TimeInputView
    private var recyclingSwitch: SwitchCompat

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        inflate(context, R.layout.recycling_day_off_layout, this)
        specifyNormTimeInputView = findViewById(R.id.specify_norm_time_input_view)
        specifyManuallyTimeInputView = findViewById(R.id.specify_manually_time_input_view)
        recyclingSwitch = findViewById(R.id.recycling_switch)
    }

    val isValid: Boolean
        get()
        {
            return when (checkedRadioButtonId)
            {
                R.id.skip_recycling_radio_button -> true
                R.id.specify_manually_radio_button -> specifyManuallyTimeInputView.isValid
                R.id.specify_norm_radio_button -> specifyNormTimeInputView.isValid
                else -> throw NotImplementedError("No other radio buttons")
            }
        }

    fun validate(): Boolean
    {
        return when (checkedRadioButtonId)
        {
            R.id.skip_recycling_radio_button -> true
            R.id.specify_manually_radio_button -> specifyManuallyTimeInputView.validate()
            R.id.specify_norm_radio_button -> specifyNormTimeInputView.validate()
            else -> throw NotImplementedError("No other radio buttons")
        }
    }

    fun getRecycling(workTime: Long): Long?
    {
        if (!isValid)
           throw VerifyError("For getRecycling RecyclingDayOffRadioGroup should be isValid")

        return when (checkedRadioButtonId)
        {
            R.id.skip_recycling_radio_button -> null
            R.id.specify_manually_radio_button ->
            {
                if (recyclingSwitch.isChecked)
                    return specifyManuallyTimeInputView.timeInMillis
                return -1 * specifyManuallyTimeInputView.timeInMillis!!
            }
            R.id.specify_norm_radio_button -> workTime - specifyManuallyTimeInputView.timeInMillis!!
            else -> throw NotImplementedError("No other radio buttons")
        }
    }
}