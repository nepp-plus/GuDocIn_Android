package com.neppplus.gudoc_in.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.gudoc_in.datas.BannerData
import com.neppplus.gudoc_in.fragments.MainBannerFragment

class MainBannerViewPagerAdapter(fm: FragmentManager, val mBannerList: List<BannerData>) :
    FragmentPagerAdapter(fm) {
    override fun getCount() = mBannerList.size

    override fun getItem(position: Int): Fragment {
        return MainBannerFragment(mBannerList[position])
    }
}



