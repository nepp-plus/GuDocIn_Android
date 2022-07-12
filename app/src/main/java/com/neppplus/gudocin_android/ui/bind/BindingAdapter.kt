package com.neppplus.gudocin_android.ui.bind

import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.neppplus.gudocin_android.R

object BindingAdapter {
    /**
     * Caution: Parameter specified as non-null is null
     */

    @JvmStatic
    @BindingAdapter("onItemSelectedListener")
    fun bindOnItemSelectedListener(
        view: BottomNavigationView,
        listener: NavigationBarView.OnItemSelectedListener?
    ) {
        view.setOnItemSelectedListener(listener)
    }

    @JvmStatic
    @BindingAdapter("loadingView", "isShow")
    fun loading(
        backgroundView: ConstraintLayout,
        loadingView: AppCompatImageView,
        isShow: Boolean
    ) {
        if (isShow) {
            val anim = AnimationUtils.loadAnimation(loadingView.context, R.anim.loading)
            loadingView.startAnimation(anim)
            backgroundView.visibility = View.VISIBLE
            loadingView.visibility = View.VISIBLE
        } else {
            backgroundView.visibility = View.GONE
            loadingView.visibility = View.GONE
        }
    }

}