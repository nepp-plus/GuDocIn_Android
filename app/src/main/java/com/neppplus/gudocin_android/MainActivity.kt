package com.neppplus.gudocin_android

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.neppplus.gudocin_android.fragments.HomeFragment
import com.neppplus.gudocin_android.fragments.MyProfileFragment
import com.neppplus.gudocin_android.fragments.RankingFragment

class MainActivity : AppCompatActivity() {

//    메인화면 - 김준기가 작업합니다.

    private val viewPager: ViewPager2 by lazy {
        findViewById(R.id.viewPager)
    }

    private val bottomNavi: BottomNavigationView by lazy {
        findViewById(R.id.bottomNavi)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.apply {
            adapter = ViewPagerAdapter(this@MainActivity)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    bottomNavi.selectedItemId = when (position) {
                        0 -> R.id.naviHome
                        1 -> R.id.naviRanking
                        else -> R.id.naviMyProfile
                    }
                }

            })

        }

        bottomNavi.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.naviHome -> viewPager.currentItem = 0
                R.id.naviRanking -> viewPager.currentItem = 1
                else -> viewPager.currentItem = 2
            }
            true
        }

    }

    inner class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount() = 3


        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> HomeFragment()
                1 -> RankingFragment()
                else -> MyProfileFragment()
            }

        }

    }

}



