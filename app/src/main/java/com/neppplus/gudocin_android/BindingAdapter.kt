package com.neppplus.gudocin_android

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

object BindingAdapter {
  /**
   * Parameter specified as non-null is null 주의
   */

  /**
   * Where to use: MainActivity
   */
  @JvmStatic
  @BindingAdapter("onItemSelectedListener")
  fun bindOnItemSelectedListener(view: BottomNavigationView, listener: NavigationBarView.OnItemSelectedListener?) {
    view.setOnItemSelectedListener(listener)
  }

  /**
   * Where to use: CartActivity
   */
  @JvmStatic
  @BindingAdapter("onRefreshListener")
  fun bindOnRefreshListener(view: SwipeRefreshLayout, listener: SwipeRefreshLayout.OnRefreshListener?) {
    view.setOnRefreshListener(listener)
  }
}