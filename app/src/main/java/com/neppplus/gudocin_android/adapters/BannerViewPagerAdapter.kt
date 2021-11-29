package com.neppplus.gudocin_android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
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