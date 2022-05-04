package com.neppplus.gudocin_android

import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

object BindingAdapter {
  @JvmStatic
  @BindingAdapter("onItemSelectedListener")
  fun bindOnItemSelectedListener(view: BottomNavigationView, listener: NavigationBarView.OnItemSelectedListener) {
    view.setOnItemSelectedListener(listener)
  }
}
