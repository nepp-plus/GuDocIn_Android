package com.neppplus.gudocin_android.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.gudocin_android.model.BannerData
import com.neppplus.gudocin_android.ui.fragment.BannerFragment

class BannerViewPagerAdapter(fm: FragmentManager, val mBannerList: List<BannerData>) :
    FragmentPagerAdapter(fm) {
    override fun getCount() = mBannerList.size

    override fun getItem(position: Int): Fragment {
        return BannerFragment(mBannerList[position])
    }
}



