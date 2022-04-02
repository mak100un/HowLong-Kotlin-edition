package com.example.howlong.widgets.buttons

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

class RegularFontButton: androidx.appcompat.widget.AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init
    {
        typeface = Typeface.createFromAsset(context.assets, "Roboto-Regular.ttf")
    }
}
