package com.neppplus.gudocin_android.view.adapter.content

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.view.presenter.fragment.content.product.ProductContentFragment
import com.neppplus.gudocin_android.view.presenter.fragment.content.store.StoreContentFragment

class ContentViewPagerAdapter(fm: FragmentManager, val mContext: Context, private val mProductData: ProductData) : FragmentPagerAdapter(fm) {

  override fun getCount() = 2

  override fun getItem(position: Int): Fragment {
    return when (position) {
      0 -> ProductContentFragment(mProductData)
      else -> StoreContentFragment(mProductData.store)
    }
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return when (position) {
      0 -> mContext.getString(R.string.product_info)
      else -> mContext.getString(R.string.vendor_info)
    }
  }
}