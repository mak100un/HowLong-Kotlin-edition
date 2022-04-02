package com.example.howlong

import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MotionEvent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.howlong.utils.KeyboardUtils
import com.example.howlong.widgets.edittexts.FocusableEditText


class MainActivity : AppCompatActivity() {
    private val currentFocusRect: Rect = Rect()
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean
    {
        if (event.action != MotionEvent.ACTION_UP ||
            currentFocus !is FocusableEditText
        )
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

    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager?.backStackEntryCount > 0)
            super.onBackPressed()
        else
        {
            AlertDialog.Builder(this)
                .setTitle(R.string.exit_alert_title)
                .setMessage(R.string.exit_alert_message)
                .setPositiveButton(R.string.yes_close
                ) { dialog, _ ->
                    dialog.dismiss()
                    System.runFinalizersOnExit(true)
                    super.onBackPressed()
                }
                .setNegativeButton(R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }
}