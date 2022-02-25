package com.neppplus.gudocin_android.activities

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityExploreProductBinding
import com.neppplus.gudocin_android.fragments.*
import com.neppplus.gudocin_android.fragments.categories.ClothesCategoryListFragment
import com.neppplus.gudocin_android.fragments.categories.FoodCategoryListFragment
import com.neppplus.gudocin_android.fragments.categories.LifeCategoryListFragment

class ExploreProductActivity : BaseActivity() {

    lateinit var binding: ActivityExploreProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_explore_product)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navEat -> binding.viewPager.currentItem = 0
                R.id.navWear -> binding.viewPager.currentItem = 1
                else -> binding.viewPager.currentItem = 2
            }
            true
        }
        binding.viewPager.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            false
        }
    }

    override fun setValues() {
        btnBack.visibility = View.VISIBLE
        txtTitleInActionBar.visibility = View.VISIBLE

        binding.viewPager.apply {
            adapter = ViewPagerAdapter(this@ExploreProductActivity)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.bottomNav.selectedItemId = when (position) {
                        0 -> R.id.navEat
                        1 -> R.id.navWear
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

        val fragmentList =
            arrayListOf(
                FoodCategoryListFragment(),
                ClothesCategoryListFragment(),
                LifeCategoryListFragment()
            )

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }

        fun getFragment(position: Int) = fragmentList[position]
    }

}