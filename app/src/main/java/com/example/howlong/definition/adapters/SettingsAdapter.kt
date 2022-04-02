package com.example.howlong.definition.adapters

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.howlong.R
import com.example.howlong.definition.adapters.base.recycler.BaseGroupedRecyclerViewAdapter
import com.example.howlong.definition.enums.RecyclerElementType
import com.example.howlong.definition.enums.SettingItemType
import com.example.howlong.definition.items.base.recycler.BaseRecyclerElement
import com.example.howlong.definition.items.settings.*
import com.example.howlong.definition.viewholders.recycler.*
import com.jakewharton.rxbinding.view.RxView
import java.lang.Error
import java.util.*
import java.util.concurrent.TimeUnit

class SettingsAdapter
(
    context: Context,
    elements: ArrayList<BaseRecyclerElement>
) : BaseGroupedRecyclerViewAdapter
<
        BaseSettingItem,
        SettingsGroupItem,
        BaseRecyclerElement,
        BaseSettingViewHolder,
        SettingsGroupViewHolder,
        RecyclerView.ViewHolder
>
(
    context,
    -1,
    R.layout.settings_recycler_group_item,
    -1,
    elements
)
{
    override fun getItemViewType(position: Int): Int {
        val element = getItem(position)
        return when (element.recyclerElementType)
        {
            RecyclerElementType.Item -> when((element as BaseSettingItem).settingItemType)
            {
                SettingItemType.Language -> R.layout.settings_recycler_two_buttons_item
                SettingItemType.Theme -> R.layout.settings_recycler_one_button_item
                SettingItemType.Notifications -> R.layout.settings_recycler_switch_item
                else -> R.layout.settings_recycler_info_item
            }
            RecyclerElementType.GroupFooter -> groupFooterLayoutRes
            RecyclerElementType.GroupHeader -> groupHeaderLayoutRes
            else -> throw NotImplementedError("Use another type of adapter")
        }
    }

    override fun onBindGroupFooterViewHolder(
        context: Context,
        holder: RecyclerView.ViewHolder,
        element: BaseRecyclerElement
    ) {
        throw NotImplementedError("No need in group footer")
    }


    override fun onBindGroupHeaderViewHolder(
        context: Context,
        holder: SettingsGroupViewHolder,
        element: SettingsGroupItem
    ) {
        holder.groupHeaderTextView.setText(element.titleRes)
    }

    override fun onBindItemViewHolder(
        context: Context,
        holder: BaseSettingViewHolder,
        element: BaseSettingItem
    )
    {
        if (!element.hasLine)
        {
            holder.dividerView.visibility = View.GONE
        }

        holder.titleTextView.setText(element.titleRes)

        when (element)
        {
            is TwoButtonsSettingItem ->
            {
                if (holder !is TwoButtonsSettingViewHolder)
                {
                    throw Error("Invalid ViewHolder type")
                }

                holder.firstButton.setText(element.firstButtonTextRes)
                setClickAction(holder.firstButton, element.firstButtonAction)

                holder.secondButton.setText(element.secondButtonTextRes)
                setClickAction(holder.secondButton, element.secondButtonAction)
            }
            is OneButtonSettingItem ->
            {
                if (holder !is OneButtonSettingViewHolder)
                {
                    throw Error("Invalid ViewHolder type")
                }

                holder.button.setImageResource(element.buttonBackground)
                setClickAction(holder.button, element.action)
            }
            is SwitchSettingItem ->
            {
                if (holder !is SwitchSettingViewHolder)
                {
                    throw Error("Invalid ViewHolder type")
                }

                holder.switch.isChecked = element.isChecked
                holder.descriptionTextView.setText(element.descriptionRes)
            }
            is InfoSettingItem ->
            {
                if (holder !is InfoSettingViewHolder)
                {
                    throw Error("Invalid ViewHolder type")
                }

                holder.descriptionTextView.setText(element.descriptionRes)
                element.action?.let {
                    holder.containerLinearLayout.isClickable = true
                    setClickAction(holder.containerLinearLayout, it) }
            }
            else -> throw NotImplementedError("Use another type of adapter")
        }
    }

    override fun onCreateElementViewHolder(view: View, layoutRes: Int): RecyclerView.ViewHolder
    {
        return when (layoutRes)
        {
            R.layout.settings_recycler_two_buttons_item -> TwoButtonsSettingViewHolder(view)
            R.layout.settings_recycler_one_button_item -> OneButtonSettingViewHolder(view)
            R.layout.settings_recycler_switch_item -> SwitchSettingViewHolder(view)
            R.layout.settings_recycler_info_item -> InfoSettingViewHolder(view)
            groupHeaderLayoutRes -> SettingsGroupViewHolder(view)
            else -> throw NotImplementedError("Use another type of adapter")
        }
    }

    private fun setClickAction(view: View, action: () -> Unit) {
        RxView.clicks(view)
            .throttleFirst(250, TimeUnit.MILLISECONDS)
            .subscribe { action() }
    }
}