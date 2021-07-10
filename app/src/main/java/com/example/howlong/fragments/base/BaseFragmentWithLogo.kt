package com.example.howlong.fragments.base
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.example.howlong.R

abstract class BaseFragmentWithLogo: BaseFragment() {

    override val toolbarViewRes: Int? = R.layout.toolbar_view_with_logo

    override fun initToolbarView(toolbar: Toolbar, view: View) {
        super.initToolbarView(toolbar, view)
        val logoButton: ImageButton = view.findViewById(R.id.logo_button)
        logoButton.setOnClickListener {
            it.findNavController().popBackStack(R.id.menuFragment, false)
        }
    }
}