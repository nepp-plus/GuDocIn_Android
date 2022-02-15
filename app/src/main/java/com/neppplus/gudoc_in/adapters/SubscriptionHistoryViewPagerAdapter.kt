package com.neppplus.gudoc_in.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.gudoc_in.fragments.PaymentListFragment
import com.neppplus.gudoc_in.fragments.ReviewListFragment

class SubscriptionHistoryViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

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