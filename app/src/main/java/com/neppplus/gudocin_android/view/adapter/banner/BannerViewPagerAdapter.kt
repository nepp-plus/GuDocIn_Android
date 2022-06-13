package com.neppplus.gudocin_android.view.adapter.banner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.gudocin_android.model.banner.BannerData
import com.neppplus.gudocin_android.view.presenter.fragment.banner.BannerFragment

class BannerViewPagerAdapter(fm: FragmentManager, private val mBannerList: List<BannerData>) :
    FragmentPagerAdapter(fm) {
    override fun getCount() = mBannerList.size

    override fun getItem(position: Int): Fragment {
        return BannerFragment(mBannerList[position])
    }
}



