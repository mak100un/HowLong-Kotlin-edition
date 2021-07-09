package com.example.howlong

import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.howlong.fragments.record.RecordFragment
import com.example.howlong.fragments.settings.SettingsFragment
import com.example.howlong.utils.KeyboardUtils
import com.example.howlong.viewmodels.settings.SettingsViewModel
import com.example.howlong.widgets.FocusableEditText

class MainActivity : AppCompatActivity() {

    private val currentFocusRect: Rect = Rect()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean
    {
        if (event.action != MotionEvent.ACTION_UP ||
            currentFocus !is FocusableEditText)
            return super.dispatchTouchEvent(event)

        val previousFocus = currentFocus as FocusableEditText

        val result = super.dispatchTouchEvent(event)

        if (previousFocus == currentFocus)
        {
            val location = IntArray(2)
            previousFocus.getLocationOnScreen(location)
            currentFocusRect.left = location[0]
            currentFocusRect.top = location[1]
            currentFocusRect.right = location[0] + previousFocus.width
            currentFocusRect.bottom = location[1] + previousFocus.height

            if (!currentFocusRect.contains(event.x.toInt(), event.y.toInt()))
            {
                KeyboardUtils.hideKeyboard(previousFocus, this)
                previousFocus.clearFocus()
            }
        }

        return  result
    }
}