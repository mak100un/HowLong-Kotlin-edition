package com.example.howlong.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DimView : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    {
        setBackgroundResource(android.R.color.black)
        alpha = 0F
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }
}