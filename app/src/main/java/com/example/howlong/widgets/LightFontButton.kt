package com.example.howlong.widgets

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.example.howlong.Utils
import com.google.android.material.internal.ViewUtils.dpToPx


class LightFontButton: androidx.appcompat.widget.AppCompatButton {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    {
        typeface = Typeface.createFromAsset(context.assets, "Roboto-Light.ttf")
    }
}
