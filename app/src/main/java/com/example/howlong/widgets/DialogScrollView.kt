package com.example.howlong.widgets

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import androidx.appcompat.app.AlertDialog
import com.example.howlong.utils.KeyboardUtils
import com.example.howlong.widgets.edittexts.FocusableEditText
import com.example.howlong.widgets.textviews.LightFontAutoCompleteTextView

class DialogScrollView : ScrollView
{
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var dialog: AlertDialog? = null

    override fun dispatchTouchEvent(event: MotionEvent): Boolean
    {
        if (event.action != MotionEvent.ACTION_UP
            || !(dialog?.currentFocus is FocusableEditText
                    || dialog?.currentFocus is LightFontAutoCompleteTextView)
        )
            return super.dispatchTouchEvent(event)

        val previousFocus = dialog?.currentFocus

        val result = super.dispatchTouchEvent(event)

        if (previousFocus == dialog?.currentFocus)
        {
            val previousLocation = IntArray(2)
            previousFocus?.getLocationInWindow(previousLocation)
            val parentLocation = IntArray(2)
            getLocationInWindow(parentLocation)
            val currentFocusRect = Rect(previousLocation[0], previousLocation[1], previousLocation[0] + (previousFocus?.width ?: 0), previousLocation[1] + (previousFocus?.height?:0))

            if (!currentFocusRect.contains(event.x.toInt() + parentLocation[0], event.y.toInt() + parentLocation[1]))
            {
                KeyboardUtils.hideKeyboard(previousFocus, context)
                previousFocus?.clearFocus()
            }
        }
        return  result
    }
}