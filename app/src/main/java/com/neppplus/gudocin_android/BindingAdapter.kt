package com.neppplus.gudocin_android

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

object BindingAdapter {
  //  MainActivity
  @JvmStatic
  @BindingAdapter("onItemSelectedListener")
  fun bindOnItemSelectedListener(view: BottomNavigationView, listener: NavigationBarView.OnItemSelectedListener) {
    view.setOnItemSelectedListener(listener)
  }

  //  CartActivity
  @JvmStatic
  @BindingAdapter("onRefreshListener")
  fun bindOnRefreshListener(view: SwipeRefreshLayout, listener: SwipeRefreshLayout.OnRefreshListener) {
    view.setOnRefreshListener(listener)
  }
}