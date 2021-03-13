package com.example.howlong.fragments.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.howlong.R
import com.example.howlong.definition.adapters.MenuItemAdapter
import com.example.howlong.definition.enums.MenuItemType
import com.example.howlong.definition.items.MenuItem
import com.example.howlong.viewmodels.history.HistoryViewModel


class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.menu_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuItems = ArrayList<MenuItem>()
        for(day in MenuItemType.values())
            menuItems.add(MenuItem(day))

        val listView = view.findViewById(R.id.menu_list) as ListView
        listView.addHeaderView(View.inflate(context, R.layout.menu_header, null), "Header", false)

        listView.adapter = MenuItemAdapter(context!!, menuItems) {menuType: MenuItemType ->
            Toast.makeText(
                context,
                "Был выбран пункт: $menuType",
                Toast.LENGTH_SHORT
            ).show()}

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}