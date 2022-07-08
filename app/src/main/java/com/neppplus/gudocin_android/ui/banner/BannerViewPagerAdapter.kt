package com.neppplus.gudocin_android.ui.banner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neppplus.gudocin_android.model.banner.BannerData

class BannerViewPagerAdapter(activity: FragmentActivity, private val mBannerList: List<BannerData>)
    : FragmentStateAdapter(activity) {

    override fun getItemCount() = mBannerList.size

    override fun createFragment(position: Int): Fragment {
        return BannerFragment(mBannerList[position])
    }

}