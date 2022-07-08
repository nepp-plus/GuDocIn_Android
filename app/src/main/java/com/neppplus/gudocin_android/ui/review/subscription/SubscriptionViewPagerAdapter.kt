package com.neppplus.gudocin_android.ui.review.subscription

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neppplus.gudocin_android.ui.payment.PaymentFragment
import com.neppplus.gudocin_android.ui.review.ReviewFragment

class SubscriptionViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val itemCount: Int = 2

    override fun getItemCount() = itemCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ReviewFragment()
            else -> PaymentFragment()
        }
    }

}