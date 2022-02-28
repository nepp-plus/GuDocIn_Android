package com.neppplus.gudocin_android.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.gudocin_android.datas.BannerData
import com.neppplus.gudocin_android.fragments.HomeBannerFragment

class HomeBannerViewPagerAdapter(fm: FragmentManager, val mBannerList: List<BannerData>) :
    FragmentPagerAdapter(fm) {
    override fun getCount() = mBannerList.size

    override fun getItem(position: Int): Fragment {
        return HomeBannerFragment(mBannerList[position])
    }
}


