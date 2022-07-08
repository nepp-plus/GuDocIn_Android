package com.neppplus.gudocin_android.ui.shopping

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityShoppingBinding
import com.neppplus.gudocin_android.ui.base.BaseActivity
import com.neppplus.gudocin_android.ui.category.clothes.ClothesCategoryFragment
import com.neppplus.gudocin_android.ui.category.food.FoodCategoryFragment
import com.neppplus.gudocin_android.ui.category.life.LifeCategoryFragment

class ShoppingActivity : BaseActivity() {

  lateinit var binding: ActivityShoppingBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
    binding.apply {
      activity = this@ShoppingActivity
      initView()
    }
  }

  private fun ActivityShoppingBinding.initView() {
    actionBarVisibility()
    viewPagerAdapter()
  }

  fun navigation(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.navFood -> binding.viewPager.currentItem = 0
      R.id.navClothes -> binding.viewPager.currentItem = 1
      else -> binding.viewPager.currentItem = 2
    }
    return true
  }

  fun viewPager(view: View) {
    view.parent.requestDisallowInterceptTouchEvent(true)
  }

  private fun actionBarVisibility() {
    shopping.visibility = View.GONE
  }

  private fun ActivityShoppingBinding.viewPagerAdapter() {
    viewPager.apply {
      adapter = ViewPagerAdapter(this@ShoppingActivity)
      registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
          super.onPageSelected(position)
          bottomNav.selectedItemId = when (position) {
            0 -> R.id.navFood
            1 -> R.id.navClothes
            else -> R.id.navLife
          }
        }
      })
      offscreenPageLimit = 3 // 3장 화면 계속 유지
    }
  }

  inner class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 3

    private val fragmentList =
      arrayListOf(FoodCategoryFragment(), ClothesCategoryFragment(), LifeCategoryFragment())

    override fun createFragment(position: Int): Fragment {
      return fragmentList[position]
    }
  }

}