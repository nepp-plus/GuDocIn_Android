package com.neppplus.gudocin_android.ui.content

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.ui.content.product.ProductContentFragment
import com.neppplus.gudocin_android.ui.content.store.StoreContentFragment

class ContentViewPagerAdapter(fm: FragmentManager, val mContext: Context) : FragmentPagerAdapter(fm) {

  override fun getCount() = 2

  override fun getItem(position: Int): Fragment {
    return when (position) {
      0 -> ProductContentFragment()
      else -> StoreContentFragment()
    }
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return when (position) {
      0 -> mContext.getString(R.string.product_info)
      else -> mContext.getString(R.string.vendor_info)
    }
  }
}