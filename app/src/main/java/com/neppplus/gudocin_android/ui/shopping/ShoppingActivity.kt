package com.neppplus.gudocin_android.ui.shopping

import android.os.Bundle
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
    setupEvents()
    setValues()
  }

  override fun setupEvents() {
    binding.bottomNav.setOnNavigationItemSelectedListener {
      when (it.itemId) {
        R.id.navFood -> binding.viewPager.currentItem = 0
        R.id.navClothes -> binding.viewPager.currentItem = 1
        else -> binding.viewPager.currentItem = 2
      }
      true
    }
    binding.viewPager.setOnTouchListener { viewPager, _ ->
      viewPager.parent.requestDisallowInterceptTouchEvent(true)
      false
    }
  }

  override fun setValues() {
    shopping.visibility = View.GONE

    binding.viewPager.apply {
      adapter = ViewPagerAdapter(this@ShoppingActivity)
      registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
          super.onPageSelected(position)
          binding.bottomNav.selectedItemId = when (position) {
            0 -> R.id.navFood
            1 -> R.id.navClothes
            else -> R.id.navLife
          }
        }
      })
    }
//        3장의 화면을 계속 유지하도록
    binding.viewPager.offscreenPageLimit = 3
  }

  inner class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount() = 3

    private val fragmentList =
      arrayListOf(FoodCategoryFragment(), ClothesCategoryFragment(), LifeCategoryFragment())

    override fun createFragment(position: Int): Fragment {
      return fragmentList[position]
    }
  }

}