package com.neppplus.gudocin_android.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.gudocin_android.fragments.PaymentListFragment
import com.neppplus.gudocin_android.fragments.ReviewListFragment

class SubscriptionViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "상품 리뷰 내역"
            else -> "상품 결제 내역"
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ReviewListFragment()
            else -> PaymentListFragment()
        }
    }

}