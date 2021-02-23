package com.example.howlong.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet


class LightFontTextView: androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    {
        typeface = Typeface.createFromAsset(context.assets, "Roboto-Light.ttf")
    }
}