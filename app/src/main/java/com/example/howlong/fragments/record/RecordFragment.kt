package com.example.howlong.fragments.record

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.LayoutDirection
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.howlong.R
import com.example.howlong.definition.dtos.TimeRecord
import com.example.howlong.definition.listeners.OnDateTimeChangedListener
import com.example.howlong.fragments.base.BaseFragmentWithLogo
import com.example.howlong.utils.TimeFormatterUtils
import com.example.howlong.viewmodels.records.RecordViewModel
import com.example.howlong.widgets.CircularProgressBar
import com.example.howlong.widgets.DialogScrollView
import com.example.howlong.widgets.pickers.DateTimePicker
import kotlinx.coroutines.*
import java.util.*
import java.util.GregorianCalendar.getInstance
import java.util.concurrent.TimeUnit


class RecordFragment : BaseFragmentWithLogo() {

    companion object {
        fun newInstance() = RecordFragment()
    }

    private var currentRecord: TimeRecord? = null
    private var currentTimerAnimation: ObjectAnimator? = null
    private var todayTotalTimerAnimation: ObjectAnimator? = null
    private var wholeTotalTimerAnimation: ObjectAnimator? = null

    private var todayTotalRecycling: Long? = null
    private var wholeTotalRecycling: Long? = null

    private lateinit var viewModel: RecordViewModel
    private lateinit var startDateTimePicker: DateTimePicker
    private lateinit var startNowButton: Button
    private lateinit var endDateTimePicker: DateTimePicker
    private lateinit var endNowButton: Button
    private lateinit var saveButton: Button

    // current
    private lateinit var currentTimerDaysTextView: TextView
    private lateinit var currentTimerTextView: TextView
    private lateinit var currentTimerHoursTextView: TextView
    private lateinit var currentTimerProgressBar: CircularProgressBar

    // todayTotal
    private lateinit var todayTotalTimerDaysTextView: TextView
    private lateinit var todayTotalTimerTextView: TextView
    private lateinit var todayTotalTimerHoursTextView: TextView
    private lateinit var todayTotalTimerProgressBar: CircularProgressBar

    // wholeTotal
    private lateinit var wholeTotalTimerDaysTextView: TextView
    private lateinit var wholeTotalTimerTextView: TextView
    private lateinit var wholeTotalTimerHoursTextView: TextView
    private lateinit var wholeTotalTimerProgressBar: CircularProgressBar

    private var timerTickJob: Job? = null

    override val fragmentRes: Int = R.layout.record_fragment

    override fun initToolbar(toolbar: Toolbar) {
        super.initToolbar(toolbar)
        toolbar.inflateMenu(R.menu.edit_item_menu)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_action -> {
                context?.let {
                    val dialog = AlertDialog.Builder(it)
                        .setTitle(R.string.test_date)
                        .setPositiveButton(R.string.save){ dialog, _ ->
                            dialog.dismiss()
                            goBack()
                        }
                        .setNegativeButton(R.string.cancel, null)
                        .setNeutralButton(R.string.apply, null)
                        .setCancelable(true)
                        .create()

                    val scrollView = dialog.layoutInflater.inflate(R.layout.record_edit_layout, null) as DialogScrollView
                    scrollView.dialog = dialog

                    dialog.setCanceledOnTouchOutside(true)
                    dialog.setView(scrollView)
                    dialog.show()
                }
                true
            }
            R.id.delete_action -> {
                goBack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initArguments() {
        super.initArguments()
        currentRecord = arguments?.let { RecordFragmentArgs.fromBundle(it).record }
    }

    override fun initFragmentWithArguments() {
        super.initFragmentWithArguments()
        currentRecord?.let {
            startDateTimePicker.dateTime = it.startDate
            endDateTimePicker.dateTime = it.endDate
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecordViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView()
    {
        cancelTimer()
        super.onDestroyView()
    }

    override fun onPause()
    {
        cancelTimer()
        super.onPause();
    }

    override fun onResume()
    {
        tryStartTimer()
        super.onResume();
    }

    override fun initFragment(view: View)
    {
        todayTotalRecycling?.let {setTime(it, todayTotalTimerDaysTextView, todayTotalTimerHoursTextView, todayTotalTimerTextView) }
        wholeTotalRecycling?.let { setTime(it, wholeTotalTimerDaysTextView, wholeTotalTimerHoursTextView, wholeTotalTimerTextView) }

        startDateTimePicker = view.findViewById(R.id.start_date_time_picker)
        startDateTimePicker.setOnDateTimeChangedListener(object : OnDateTimeChangedListener()
        {
            override fun onDateTimeChanged(dateTime: Calendar) {
                Toast.makeText(context, "Дата и время установлены", Toast.LENGTH_SHORT).show()

                checkEndDateTime()
            }
        })
        startNowButton = view.findViewById(R.id.start_now_button)
        startNowButton.setOnClickListener { _ -> startDateTimePicker.dateTime = getInstance()
            checkEndDateTime()
        }

        endDateTimePicker = view.findViewById(R.id.end_date_time_picker)
        endDateTimePicker.setOnDateTimeChangedListener(object : OnDateTimeChangedListener()
        {
            override fun onDateTimeChanged(dateTime: Calendar) {
                Toast.makeText(context, "Дата и время установлены", Toast.LENGTH_SHORT).show()

                checkStartDateTime()
            }
        })
        endNowButton = view.findViewById(R.id.end_now_button)
        endNowButton.setOnClickListener { _ -> endDateTimePicker.dateTime = getInstance()
            checkStartDateTime()
        }

        saveButton = view.findViewById(R.id.save_record_button)

        saveButton.setOnClickListener { view ->
            if (!startDateTimePicker.validate())
                return@setOnClickListener

            if (!endDateTimePicker.isValid)
            {
                endDateTimePicker.dateTime = getInstance()
                checkStartDateTime()
            }

            Toast.makeText(context, "Запись сохранена", Toast.LENGTH_SHORT).show()

            view.findNavController().navigateUp()
        }

        // current
        currentTimerDaysTextView = view.findViewById(R.id.current_timer_days_textview)
        currentTimerTextView = view.findViewById(R.id.current_timer_textview)
        currentTimerHoursTextView = view.findViewById(R.id.current_timer_hours_textview)
        currentTimerProgressBar = view.findViewById(R.id.current_timer_progressbar)

        // todayTotal
        todayTotalTimerDaysTextView = view.findViewById(R.id.today_total_timer_days_textview)
        todayTotalTimerTextView = view.findViewById(R.id.today_total_timer_textview)
        todayTotalTimerHoursTextView = view.findViewById(R.id.today_total_timer_hours_textview)
        todayTotalTimerProgressBar = view.findViewById(R.id.today_total_timer_progressbar)

        // wholeTotal
        wholeTotalTimerDaysTextView = view.findViewById(R.id.whole_total_timer_days_textview)
        wholeTotalTimerTextView = view.findViewById(R.id.whole_total_timer_textview)
        wholeTotalTimerHoursTextView = view.findViewById(R.id.whole_total_timer_hours_textview)
        wholeTotalTimerProgressBar = view.findViewById(R.id.whole_total_timer_progressbar)

        todayTotalRecycling = 9054000 // 2,5 h
        wholeTotalRecycling = 99021000 // 1d 3,5 h

        todayTotalRecycling?.let { setTime(it, todayTotalTimerDaysTextView, todayTotalTimerHoursTextView, todayTotalTimerTextView)
            todayTotalTimerProgressBar.progress = getTimerProgress(it)}
        wholeTotalRecycling?.let { setTime(it, wholeTotalTimerDaysTextView, wholeTotalTimerHoursTextView, wholeTotalTimerTextView)
            wholeTotalTimerProgressBar.progress = getTimerProgress(it)}
    }

    private fun checkStartDateTime()
    {
        if (startDateTimePicker.isValid &&
            endDateTimePicker.dateTime!! < startDateTimePicker.dateTime)
            startDateTimePicker.dateTime = endDateTimePicker.dateTime

        tryStartTimer()
    }

    private fun checkEndDateTime()
    {
        if (endDateTimePicker.isValid &&
            startDateTimePicker.dateTime!! > endDateTimePicker.dateTime)
            endDateTimePicker.dateTime = startDateTimePicker.dateTime

        tryStartTimer()
    }

    private fun tickTimer()
    {
        timerTickJob = GlobalScope.launch {
            delay(1000)
            ensureActive()

            withContext(Dispatchers.Main)
            {
                setTime(getCurrentTimeDiffInMillis())
            }
            tickTimer()
        }
    }

    private fun tryStartTimer()
    {
        if (!startDateTimePicker.isValid)
            return

        cancelTimer()

        var timeDiffInMillis = getCurrentTimeDiffInMillis()
        var startTimer = true

        if (timeDiffInMillis < 0)
        {
            timeDiffInMillis = 0
            startTimer = false
        }

        currentTimerProgressBar.progress = getTimerProgress(timeDiffInMillis)
        todayTotalRecycling?.let { todayTotalTimerProgressBar.progress = getTimerProgress(timeDiffInMillis + it) }
        wholeTotalRecycling?.let { wholeTotalTimerProgressBar.progress = getTimerProgress(timeDiffInMillis + it) }

        setTime(timeDiffInMillis)

        if (!endDateTimePicker.isValid &&
            startTimer)
        {
            tickTimer()

            currentTimerAnimation = createProgressAnimator(currentTimerProgressBar, currentTimerProgressBar.progress)
            currentTimerAnimation?.start()

            todayTotalRecycling?.let { todayTotalTimerAnimation = createProgressAnimator(todayTotalTimerProgressBar, todayTotalTimerProgressBar.progress)
                todayTotalTimerAnimation?.start() }

            wholeTotalRecycling?.let { wholeTotalTimerAnimation = createProgressAnimator(wholeTotalTimerProgressBar, wholeTotalTimerProgressBar.progress)
                wholeTotalTimerAnimation?.start() }
        }
    }

    private fun setTime(timeDiffInMillis: Long)
    {
        if (!startDateTimePicker.isValid)
            return

        setTime(timeDiffInMillis, currentTimerDaysTextView, currentTimerHoursTextView, currentTimerTextView)
        todayTotalRecycling?.let {setTime(timeDiffInMillis + it, todayTotalTimerDaysTextView, todayTotalTimerHoursTextView, todayTotalTimerTextView) }
        wholeTotalRecycling?.let { setTime(timeDiffInMillis + it, wholeTotalTimerDaysTextView, wholeTotalTimerHoursTextView, wholeTotalTimerTextView) }
    }

    private fun setTime(timeInMillis: Long, timerDaysTextView: TextView, timerHoursTextView: TextView, timerTextView: TextView)
    {
        timerDaysTextView.text = TimeFormatterUtils.ToNonZerodWithPostfixFormat(timeInMillis)
        timerHoursTextView.text = TimeFormatterUtils.ToNoneZeroHWithPostfixFormat(timeInMillis)
        timerTextView.text = TimeFormatterUtils.TommssFormat(timeInMillis)
    }

    private fun cancelTimer() {
        if (currentTimerAnimation?.isRunning == true) {
            currentTimerAnimation?.interpolator = null
            currentTimerAnimation?.cancel()
        }
        currentTimerAnimation = null

        if (todayTotalTimerAnimation?.isRunning == true) {
            todayTotalTimerAnimation?.interpolator = null
            todayTotalTimerAnimation?.cancel()
        }
        todayTotalTimerAnimation = null

        if (wholeTotalTimerAnimation?.isRunning == true) {
            wholeTotalTimerAnimation?.interpolator = null
            wholeTotalTimerAnimation?.cancel()
        }
        wholeTotalTimerAnimation = null

        currentTimerProgressBar.clearAnimation()
        todayTotalTimerProgressBar.clearAnimation()
        wholeTotalTimerProgressBar.clearAnimation()
        timerTickJob?.cancel()
        timerTickJob = null
    }

    private fun createProgressAnimator(progressBar: CircularProgressBar, progress: Float): ObjectAnimator
    {
        val progressAnimator = ObjectAnimator.ofFloat(progressBar, "progress", progress, progress + 60)
        progressAnimator?.duration = 60000
        progressAnimator?.repeatMode = ValueAnimator.RESTART
        progressAnimator?.repeatCount = Animation.INFINITE
        progressAnimator?.interpolator = LinearInterpolator()

        return  progressAnimator
    }

    private fun getTimerProgress(timeInMillis: Long): Float
    {
        return (TimeUnit.MILLISECONDS.toSeconds(timeInMillis) - TimeUnit.MILLISECONDS.toMinutes(timeInMillis) * 60).toFloat()
    }

    private fun getCurrentTimeDiffInMillis(): Long
    {
        val currentDateTime = getInstance()
        if (startDateTimePicker.dateTime!!.get(Calendar.YEAR) != currentDateTime.get(Calendar.YEAR) ||
            startDateTimePicker.dateTime!!.get(Calendar.MONTH) != currentDateTime.get(Calendar.MONTH) ||
            startDateTimePicker.dateTime!!.get(Calendar.DAY_OF_MONTH) != currentDateTime.get(Calendar.DAY_OF_MONTH))
            todayTotalRecycling = 0

        return (endDateTimePicker.dateTime?.time?.time ?: currentDateTime.time.time) - startDateTimePicker.dateTime!!.time.time
    }
}