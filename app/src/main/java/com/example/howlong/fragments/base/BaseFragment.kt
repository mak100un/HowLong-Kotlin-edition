package com.example.howlong.fragments.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.howlong.R


abstract class BaseFragment : Fragment() {

    private var _view: View? = null
    private val inInitialized : Boolean get() = _view != null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (inInitialized)
        {
            return _view
        }

        return inflater.inflate(fragmentRes, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (inInitialized)
        {
            return
        }
        _view = view
        initArguments()
        initToolbar(view)
        initFragment(view)
        initFragmentWithArguments()
    }

    protected open fun onToolBarBackClicked() {
        view?.findNavController()?.navigateUp()
    }

    abstract val fragmentRes: Int

    open val toolbarViewRes: Int? = null

    open val toolbarTitleRes: Int? = null

    open val hasToolbar: Boolean = true

    open val hasNavigationIcon: Boolean = true

    open fun initArguments()
    {

    }

    open fun initToolbar(view: View)
    {
        if (!hasToolbar)
            return

        view.findViewById<Toolbar>(R.id.toolbar)?.let {
            initToolbar(it)
        }
    }

    open fun initToolbar(toolbar: Toolbar)
    {
        toolbarTitleRes?.let {
            toolbar.setTitle(it)
        }

        toolbarViewRes?.let {
            initToolbarView(toolbar, layoutInflater.inflate(it, null))
        }

        if (!hasNavigationIcon)
            return

        context?.getDrawableFromAttribute(R.attr.homeAsUpIndicator)?.let {
            DrawableCompat.setTint(it, Color.WHITE)
            toolbar.navigationIcon = it
        }

        toolbar.setNavigationOnClickListener{
            onToolBarBackClicked()
        }
    }

    open fun initToolbarView(toolbar: Toolbar, view: View) {
        toolbar.addView(view, Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT))
    }

    open fun initFragmentWithArguments()
    {

    }

    protected fun goBack()
    {
        view?.findNavController()?.navigateUp()
    }

    abstract fun initFragment(view: View)

    private fun Context.getDrawableFromAttribute(attributeId: Int): Drawable {
        val typedValue = TypedValue().also { theme.resolveAttribute(attributeId, it, true) }
        return resources.getDrawable(typedValue.resourceId, theme)
    }
}