package com.example.howlong.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.howlong.R

class ToolbarShadowView: View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setBackgroundResource(R.drawable.toolbar_shadow)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }
}