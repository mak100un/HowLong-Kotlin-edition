package com.example.howlong.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.R
import com.example.howlong.definition.adapters.MenuItemAdapter
import com.example.howlong.definition.adapters.SettingsAdapter
import com.example.howlong.definition.enums.MenuItemType
import com.example.howlong.definition.enums.SettingItemType
import com.example.howlong.definition.enums.SettingsGroupingType
import com.example.howlong.definition.items.MenuItem
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement
import com.example.howlong.definition.items.settings.*
import com.example.howlong.fragments.base.BaseFragment
import com.example.howlong.viewmodels.settings.SettingsViewModel

class SettingsFragment : BaseFragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override val fragmentRes: Int = R.layout.settings_fragment

    override fun initFragment(view: View) {
        initSettingsRecycler(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun initSettingsRecycler(view: View)
    {
        val settingItems = ArrayList<BaseRecyclerElement>()
        for(settingType in SettingItemType.values())
        {
            when (settingType) {
                // SettingsGroupingType.Main
                SettingItemType.Language ->
                {
                    settingItems.add(SettingsGroupItem(SettingsGroupingType.Main))
                    settingItems.add(TwoButtonsSettingItem(settingType, {}, R.string.russian, {}, R.string.english))
                }
                SettingItemType.Theme -> settingItems.add(OneButtonSettingItem(settingType, {}, R.drawable.ic_moon, false))
                // SettingsGroupingType.Other
                SettingItemType.Notifications ->
                {
                    settingItems.add(SettingsGroupItem(SettingsGroupingType.Other))
                    settingItems.add(SwitchSettingItem(settingType, false))
                }
                SettingItemType.CleanHistory -> settingItems.add(InfoSettingItem(settingType, {}, false))
                // SettingsGroupingType.About
                SettingItemType.Version ->
                {
                    settingItems.add(SettingsGroupItem(SettingsGroupingType.About))
                    settingItems.add(InfoSettingItem(settingType))
                }
                SettingItemType.Support -> settingItems.add(InfoSettingItem(settingType, {}))
                SettingItemType.Improvement -> settingItems.add(InfoSettingItem(settingType, {}))
                SettingItemType.Developer -> settingItems.add(InfoSettingItem(settingType, {}, false))
            }
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.settings_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = SettingsAdapter(context!!, settingItems)
    }
}