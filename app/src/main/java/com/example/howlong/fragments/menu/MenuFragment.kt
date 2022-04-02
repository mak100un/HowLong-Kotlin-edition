package com.example.howlong.fragments.menu

import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.howlong.R
import com.example.howlong.definition.adapters.MenuItemAdapter
import com.example.howlong.definition.enums.MenuItemType
import com.example.howlong.definition.items.MenuItem
import com.example.howlong.fragments.base.BaseFragment
import com.example.howlong.utils.TimeFormatterUtils
import com.example.howlong.viewmodels.menu.MenuViewModel
import com.example.howlong.widgets.textviews.LightFontTextView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MenuFragment : BaseFragment() {

    companion object {
        fun newInstance() = MenuFragment()
    }

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy, ", Locale("ru"))

    private lateinit var viewModel: MenuViewModel
    private lateinit var currentDayTextView: LightFontTextView

    override val fragmentRes: Int = R.layout.menu_fragment

    override val toolbarTitleRes: Int? = R.string.app_name

    override val hasNavigationIcon: Boolean = false

    override fun initFragment(view: View) {
        currentDayTextView = view.findViewById(R.id.current_day_text_view)
        updateCurrentDay()

        val menuItems = ArrayList<MenuItem>()
        for(day in MenuItemType.values())
            menuItems.add(MenuItem(day))

        val listView: ListView = view.findViewById(R.id.menu_list)
        listView.addHeaderView(View.inflate(context, R.layout.menu_header, null), "Header", false)

        listView.adapter = MenuItemAdapter(context!!, menuItems) {view: View, menuType: MenuItemType ->
            view.findNavController().navigate(when(menuType)
            {
                MenuItemType.ActiveRecord -> R.id.action_menuFragment_to_recordFragment
                MenuItemType.History -> R.id.action_menuFragment_to_historyFragment
                MenuItemType.Settings -> R.id.action_menuFragment_to_settingsFragment
            })}
    }

    override fun onResume() {
        super.onResume()
        updateCurrentDay()
    }

    private fun updateCurrentDay() {
        val today = GregorianCalendar.getInstance()
        currentDayTextView.text = dateFormatter.format(today.time) +  TimeFormatterUtils.GetDayOfWeekShortName(today.get(Calendar.DAY_OF_WEEK)) + ", Рабочий"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        // TODO: Use the ViewModel
    }

}