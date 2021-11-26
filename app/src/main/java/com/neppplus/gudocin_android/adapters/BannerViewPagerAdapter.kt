package com.neppplus.gudocin_android.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.gudocin_android.fragments.MainBannerFragment

class BannerViewPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){

    val bannerImg = ArrayList<String>()

    override fun getCount()= bannerImg.size

    override fun getItem(position: Int): Fragment {

        return when(position){

            0->MainBannerFragment()
            else ->MainBannerFragment()
        }

    }

}