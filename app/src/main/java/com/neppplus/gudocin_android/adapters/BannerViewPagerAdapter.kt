package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.BannerData
import com.neppplus.gudocin_android.fragments.MainBannerFragment

class BannerViewPagerAdapter(fm: FragmentManager, val mBannerList: List<BannerData>)
    :FragmentPagerAdapter(fm) {
    override fun getCount()= mBannerList.size

    override fun getItem(position: Int): Fragment {
        return MainBannerFragment( mBannerList[position] )
    }


}



