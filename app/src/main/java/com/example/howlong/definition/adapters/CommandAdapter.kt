package com.example.howlong.definition.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE
import android.text.TextPaint
import android.text.style.MetricAffectingSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.howlong.R
import com.example.howlong.definition.adapters.base.recycler.BaseRecyclerViewHolder
import com.example.howlong.definition.dtos.CommandDto
import com.example.howlong.definition.viewholders.recycler.CommandViewHolder
import java.util.*

class CommandAdapter
(
    context: Context,
    elements: ArrayList<CommandDto>
): BaseRecyclerViewHolder
<
    CommandDto,
    CommandViewHolder
>
    (
        context,
        R.layout.command_item_layout,
        elements
    )
{
    override fun onBindItemViewHolder(
        context: Context,
        holder: CommandViewHolder,
        element: CommandDto
    ) {
        val spannableString = SpannableString(element.name + element.description)

        spannableString.setSpan(RegularFontSpan(context, holder.commandTextView, 18F, ContextCompat.getColor(context, R.color.colorPrimary)), 0, element.name.length, SPAN_INCLUSIVE_EXCLUSIVE)
        //spannableString.setSpan(StyleSpan(Typeface.ITALIC), 0, element.name.length, SPAN_INCLUSIVE_EXCLUSIVE)
        holder.commandTextView.text = spannableString
        holder.commandTextView.fixTextSelection()
    }

    fun TextView.fixTextSelection() {
        setTextIsSelectable(false)
        post { setTextIsSelectable(true) }
    }

    override fun onCreateElementViewHolder(view: View, layoutRes: Int): CommandViewHolder
    {
        return CommandViewHolder(view)
    }
}

class RegularFontSpan(val context: Context, val textView: TextView, private val fontSize: Float? = null, private val color: Int? = null) : MetricAffectingSpan()
{
    override fun updateDrawState(paint: TextPaint?) {
        paint?.let{
            apply(it)
        }
    }

    override fun updateMeasureState(paint: TextPaint) {
        apply(paint)
    }

    fun apply(paint: TextPaint)
    {
        paint.typeface = Typeface.createFromAsset(context.assets, "Roboto-Regular.ttf")
        fontSize?.let { paint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, it, textView.resources.displayMetrics) }
        color?.let { paint.color = it }
    }
}