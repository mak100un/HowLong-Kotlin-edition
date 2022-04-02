package com.example.howlong.widgets.textviews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

class LightFontAutoCompleteTextView: androidx.appcompat.widget.AppCompatAutoCompleteTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init
    {
        typeface = Typeface.createFromAsset(context.assets, "Roboto-Light.ttf")
    }
}