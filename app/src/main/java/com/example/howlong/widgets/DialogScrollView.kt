package com.example.howlong.widgets

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import androidx.appcompat.app.AlertDialog
import com.example.howlong.utils.KeyboardUtils
import com.example.howlong.widgets.edittexts.FocusableEditText

class DialogScrollView : ScrollView
{
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val currentFocusRect: Rect = Rect()

    var dialog: AlertDialog? = null

    override fun dispatchTouchEvent(event: MotionEvent): Boolean
    {
        if (event.action != MotionEvent.ACTION_UP ||
            dialog?.currentFocus !is FocusableEditText
        )
            return super.dispatchTouchEvent(event)

        val previousFocus = dialog?.currentFocus as? FocusableEditText

        val result = super.dispatchTouchEvent(event)

        if (previousFocus == dialog?.currentFocus)
        {
            val location = IntArray(2)
            previousFocus?.getLocationOnScreen(location)
            currentFocusRect.left = location[0]
            currentFocusRect.top = location[1]
            currentFocusRect.right = location[0] + (previousFocus?.width ?: 0)
            currentFocusRect.bottom = location[1] + (previousFocus?.height?:0)

            if (!currentFocusRect.contains(event.x.toInt(), event.y.toInt()))
            {
                KeyboardUtils.hideKeyboard(previousFocus, context)
                previousFocus?.clearFocus()
            }
        }
        return  result
    }
}