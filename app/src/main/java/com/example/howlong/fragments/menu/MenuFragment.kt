package com.example.howlong.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.howlong.R
import com.example.howlong.definition.adapters.MenuItemAdapter
import com.example.howlong.definition.enums.MenuItemType
import com.example.howlong.definition.items.MenuItem
import com.example.howlong.fragments.base.BaseFragment
import com.example.howlong.viewmodels.menu.MenuViewModel


class MenuFragment : BaseFragment() {

    companion object {
        fun newInstance() = MenuFragment()
    }

    private lateinit var viewModel: MenuViewModel

    override val fragmentRes: Int = R.layout.menu_fragment

    override fun initFragment(view: View) {
        val menuItems = ArrayList<MenuItem>()
        for(day in MenuItemType.values())
            menuItems.add(MenuItem(day))

        val listView: ListView = view.findViewById(R.id.menu_list)
        listView.addHeaderView(View.inflate(context, R.layout.menu_header, null), "Header", false)

        listView.adapter = MenuItemAdapter(context!!, menuItems) {view: View, menuType: MenuItemType ->
            when(menuType)
            {
                MenuItemType.ActiveRecord -> view.findNavController().navigate(R.id.action_menuFragment_to_recordFragment)
                MenuItemType.History -> view.findNavController().navigate(R.id.action_menuFragment_to_historyFragment)
                MenuItemType.Settings -> view.findNavController().navigate(R.id.action_menuFragment_to_settingsFragment)
            }}
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        // TODO: Use the ViewModel
    }

}