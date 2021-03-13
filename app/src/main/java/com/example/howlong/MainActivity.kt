package com.example.howlong

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.howlong.fragments.history.HistoryFragment
import com.example.howlong.fragments.menu.MenuFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setHomeButtonEnabled(true)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HistoryFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }
}