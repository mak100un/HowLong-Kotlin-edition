package com.example.howlong.fragments.groups

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.R
import com.example.howlong.definition.adapters.HistoryAdapter
import com.example.howlong.definition.dtos.TimeRecord
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement
import com.example.howlong.fragments.base.BaseFragmentWithLogo
import com.example.howlong.fragments.history.HistoryFragment
import com.example.howlong.fragments.history.HistoryFragmentDirections
import com.example.howlong.viewmodels.groups.GroupViewModel
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class GroupFragment : BaseFragmentWithLogo() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: GroupViewModel
    private var records: ArrayList<BaseRecyclerElement> = ArrayList()
    private val random = Random()

    override val fragmentRes: Int = R.layout.group_fragment

    override fun initFragment(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.records_recycler)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        addRecordsPage()

        val adapter = HistoryAdapter(context!!, records, { view: View, record: TimeRecord ->
            val action = GroupFragmentDirections.actionGroupFragmentToRecordFragment()
            action.record = record
            view.findNavController().navigate(action) })
        recyclerView.adapter = adapter

        val saveButton: Button = view.findViewById(R.id.save_record_button)

        saveButton.setOnClickListener { view ->
            Toast.makeText(context, "Группа сохранена", Toast.LENGTH_SHORT).show()

            view.findNavController().navigateUp()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GroupViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun addRecordsPage()
    {
        val now = GregorianCalendar.getInstance()
        val year = now.get(Calendar.YEAR)
        val month = now.get(Calendar.MONTH)
        val day = now.get(Calendar.DAY_OF_MONTH)
        for (i in 0..2)
        {
            val startHour = random.nextInt(22)
            val startMinutes = random.nextInt(59)
            var endHour = random.nextInt(23 - startHour) + startHour
            var endMinutes = random.nextInt(59)

            if (startHour == endHour)
                endHour++

            val startDate = GregorianCalendar(year, month, day, startHour, startMinutes)
            val endDate = GregorianCalendar(year, month, day, endHour, endMinutes)

            startDate.add(Calendar.DAY_OF_YEAR, i)
            endDate.add(Calendar.DAY_OF_YEAR, i)

            records.add(TimeRecord(startDate, endDate, (endDate.time.time - startDate.time.time) - TimeUnit.HOURS.toMillis(8)))

        }
    }

}