package com.example.howlong.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(fragmentRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArguments()
        initFragment(view)
        initFragmentWithArguments()
    }

    abstract val fragmentRes: Int

    open fun initArguments()
    {

    }

    open fun initFragmentWithArguments()
    {

    }

    abstract fun initFragment(view: View)
}