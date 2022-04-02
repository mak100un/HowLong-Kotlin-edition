package com.example.howlong.fragments.history

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.howlong.R
import com.example.howlong.definition.adapters.HistoryAdapter
import com.example.howlong.definition.adapters.HistoryStatisticsAdapter
import com.example.howlong.definition.dtos.TimeRecord
import com.example.howlong.definition.enums.HistoryGroupingType
import com.example.howlong.definition.enums.HistoryStatisticType
import com.example.howlong.definition.items.HistoryGroupItem
import com.example.howlong.definition.items.HistoryStatistic
import com.example.howlong.definition.items.LoadingItem
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement
import com.example.howlong.definition.listeners.RecyclerPaginationListener
import com.example.howlong.fragments.base.BaseFragmentWithLogo
import com.example.howlong.viewmodels.history.HistoryViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class HistoryFragment : BaseFragmentWithLogo() {

    companion object {
        fun newInstance() = HistoryFragment()
        const val MAX_DIM_ALPHA = 0.8F
    }

    private lateinit var viewModel: HistoryViewModel
    private lateinit var addRecordButton: FloatingActionButton
    private var lastRecordDate: Calendar = GregorianCalendar.getInstance()
    private var records: ArrayList<BaseRecyclerElement> = ArrayList()
    private val random = Random()
    private var loadingMoreJob: Job? = null

    override val fragmentRes: Int = R.layout.history_fragment

    override fun initFragment(view: View) {
        addRecordButton = view.findViewById(R.id.add_record_button)
        addRecordButton.setOnClickListener { view: View -> view.findNavController().navigate(R.id.action_historyFragment_to_recordFragment) }
        initHistoryRecycler(view)
        initBottomSheet(view)
    }

    override fun onDestroyView()
    {
        loadingMoreJob?.cancel()
        loadingMoreJob = null
        super.onDestroyView()
    }

    private fun initBottomSheet(view: View) {
        val statisticsItems = ArrayList<HistoryStatistic>()
        for(statisticType in HistoryStatisticType.values())
        {
            statisticsItems.add(
                HistoryStatistic(
                    statisticType, when (statisticType) {
                        HistoryStatisticType.Recycling -> {
                            5
                        }
                        HistoryStatisticType.Flaw -> {
                            3
                        }
                        HistoryStatisticType.All -> {
                            8
                        }
                    }
                )
            )
        }

        val statisticsListView: ListView = view.findViewById(R.id.statistics_list)
        statisticsListView.addHeaderView(View.inflate(context, R.layout.history_bottom_sheet_header, null), "Header", false)
        statisticsListView.addFooterView(View.inflate(context, R.layout.history_bottom_sheet_footer, null), "Footer", false)

        statisticsListView.adapter = HistoryStatisticsAdapter(context!!, statisticsItems)

        val commonResultView: TextView= view.findViewById(R.id.common_result)
        commonResultView?.text = "05:00"
        commonResultView.setTextColor(ContextCompat.getColor(context!!, R.color.colorRed))

        val resultPlaceholderView: TextView = view.findViewById(R.id.common_result_placeholder)
        resultPlaceholderView?.setText(R.string.day_off)

        val dimView : View = view.findViewById(R.id.dim_view)
        val bottomSheetBehavior = BottomSheetBehavior.from<View>(view.findViewById(R.id.bottom_sheet))

        dimView.setOnTouchListener { _, _ ->
            val notCollapsed = bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED
            if (notCollapsed)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            notCollapsed
        }

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_COLLAPSED ->
                    {
                        if (!addRecordButton.isShown)
                            addRecordButton.show()
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        if (addRecordButton.isShown)
                            addRecordButton.hide()
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                dimView.alpha = when (slideOffset > MAX_DIM_ALPHA) {
                    true -> MAX_DIM_ALPHA
                    else -> slideOffset
                }
            }
        })
    }

    private fun initHistoryRecycler(view: View) {

        val recyclerView: RecyclerView = view.findViewById(R.id.history_recycler)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        addRecordsPage(true)

        val adapter = HistoryAdapter(context!!, records, { view: View, record: TimeRecord ->
            val action = HistoryFragmentDirections.actionHistoryFragmentToRecordFragment()
            action.record = record
            view.findNavController().navigate(action) }, { view: View, _: HistoryGroupItem ->

            view.findNavController().navigate(R.id.action_historyFragment_to_groupFragment) })
        recyclerView.adapter = adapter


        val swipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.history_swipe_refresh_layout)

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))

        swipeRefreshLayout.setOnRefreshListener {
            GlobalScope.launch {
                withContext(Dispatchers.Main)
                {
                    loadingMoreJob?.cancel()
                    loadingMoreJob?.join()
                    loadingMoreJob = null
                    lastRecordDate = GregorianCalendar.getInstance()
                    adapter.clear()
                }
                delay(1500)
                withContext(Dispatchers.Main)
                {
                    addRecordsPage(true)
                    adapter.notifyDataSetChanged()
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        recyclerView.setOnScrollListener(object : RecyclerPaginationListener(layoutManager, 50,
            { _: RecyclerView, _: Int, dy: Int ->
                run {
                    if (dy > 0
                        && addRecordButton.isShown)
                        addRecordButton.hide()
                    else if (dy < 0
                        && !addRecordButton.isShown)
                        addRecordButton.show()
                }
            }
        )
        {
            override fun onNeedLoadMore(): Boolean {
                return !swipeRefreshLayout.isRefreshing &&
                        loadingMoreJob?.isActive != true
            }

            override fun onLoadMore()
            {
                loadingMoreJob = GlobalScope.launch {
                    withContext(Dispatchers.Main)
                    {
                        adapter.addElement(LoadingItem())
                    }
                    delay(1500)
                    withContext(Dispatchers.Main)
                    {
                        records.firstOrNull { element -> element is LoadingItem }?.let { records.remove(it)
                            addRecordsPage()
                            adapter.notifyDataSetChanged() }
                    }
                }
            }
        })

    }

    private fun addRecordsPage(addStartHeader: Boolean = false)
    {
        if (addStartHeader &&
            lastRecordDate.get(Calendar.DATE) != 1)
            records.add(HistoryGroupItem(GregorianCalendar(lastRecordDate.get(Calendar.YEAR), lastRecordDate.get(Calendar.MONTH), 1), HistoryGroupingType.Month))

        var addedDay = 0

        for(year in (lastRecordDate.get(Calendar.YEAR))..Int.MAX_VALUE)
        {
            for(month in (lastRecordDate.get(Calendar.MONTH))..11)
            {
                for(day in (lastRecordDate.get(Calendar.DAY_OF_MONTH))..GregorianCalendar(year, month, 1).getActualMaximum(Calendar.DAY_OF_MONTH))
                {
                    addedDay++
                    lastRecordDate.add(Calendar.DAY_OF_YEAR, 1)

                    if (day == 1)
                        records.add(HistoryGroupItem(GregorianCalendar(year, month, day), HistoryGroupingType.Month))

                    val startHour = random.nextInt(22)
                    val startMinutes = random.nextInt(59)
                    var endHour = random.nextInt(23 - startHour) + startHour
                    var endMinutes = random.nextInt(59)

                    if (startHour == endHour)
                        endHour++

                    val startDate = GregorianCalendar(year, month, day, startHour, startMinutes)
                    val endDate = GregorianCalendar(year, month, day, endHour, endMinutes)

                    records.add(TimeRecord(startDate, endDate, (endDate.time.time - startDate.time.time) - TimeUnit.HOURS.toMillis(8)))

                    if (addedDay == 100)
                        return
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}