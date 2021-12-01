package com.neppplus.gudocin_android.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.gudocin_android.fragments.PurchastListFragment
import com.neppplus.gudocin_android.fragments.TestReviewListFragment

class PurchaseViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {

        return when(position) {
            0 -> PurchastListFragment()
            else -> TestReviewListFragment()
        }

    }
}