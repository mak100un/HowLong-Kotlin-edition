package com.example.howlong.widgets.pickers

import android.app.AlertDialog
import android.content.Context
import android.graphics.Rect
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import com.example.howlong.utils.KeyboardUtils
import com.example.howlong.widgets.edittexts.FocusableEditText

abstract class BasePickerEditText:
    FocusableEditText {
    private var dialog: AlertDialog? = null
    private val currentFocusRect: Rect = Rect()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init
    {
        inputType = InputType.TYPE_NULL
        isCursorVisible = false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        if (!isFocused && event.action == MotionEvent.ACTION_UP)
        {
            currentFocusRect.left = 0
            currentFocusRect.top = 0
            currentFocusRect.right = width
            currentFocusRect.bottom = height

            if (currentFocusRect.contains(event.x.toInt(), event.y.toInt()))
            {
                KeyboardUtils.hideKeyboard(this, context)
                onShowDialog()
            }
        }

        return super.onTouchEvent(event)
    }

    protected fun onShowDialog() {
        if (dialog?.isShowing == true)
        {
            return
        }

        dialog = onCreateDialog()

        dialog?.setOnCancelListener { onDismissDialog() }
        dialog?.show()
    }

    protected fun onDismissDialog()
    {
        clearFocus()
        dialog?.setOnCancelListener(null)
        dialog?.dismiss()
        dialog = null
    }

    protected abstract fun onCreateDialog() : AlertDialog
}