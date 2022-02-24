package com.neppplus.gudocin_android.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.fragments.contents.ProductContentFragment
import com.neppplus.gudocin_android.fragments.contents.StoreContentFragment

class ContentViewPagerAdapter(fm: FragmentManager, val mProductData: ProductData) :
    FragmentPagerAdapter(fm) {
    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ProductContentFragment(mProductData)
            else -> StoreContentFragment(mProductData.store)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "상품 정보"
            else -> "판매자 정보"
        }
    }
}