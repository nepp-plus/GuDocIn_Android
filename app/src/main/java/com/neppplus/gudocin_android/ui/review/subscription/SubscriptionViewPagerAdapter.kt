package com.neppplus.gudocin_android.ui.review.subscription

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.ui.payment.PaymentFragment
import com.neppplus.gudocin_android.ui.review.ReviewFragment

class SubscriptionViewPagerAdapter(fm: FragmentManager, private val mContext: Context) : FragmentPagerAdapter(fm) {

  override fun getCount() = 2

  override fun getItem(position: Int): Fragment {
    return when (position) {
      0 -> ReviewFragment()
      else -> PaymentFragment()
    }
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return when (position) {
      0 -> mContext.getString(R.string.product_review)
      else -> mContext.getString(R.string.product_subscribe)
    }
  }

}