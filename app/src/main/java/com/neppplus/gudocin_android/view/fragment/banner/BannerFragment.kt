package com.neppplus.gudocin_android.view.fragment.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentBannerBinding
import com.neppplus.gudocin_android.model.banner.BannerData
import com.neppplus.gudocin_android.view.fragment.BaseFragment

class BannerFragment(private val mBannerData: BannerData? = null) : BaseFragment() {

    lateinit var binding: FragmentBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_banner, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        /* binding.imgBanner.setOnClickListener {
             val myUri = Uri.parse(mBannerData.clickUrl)
             val myIntent = Intent(Intent.ACTION_VIEW, myUri)
             startActivity(myIntent)
         } */
    }

    override fun setValues() {
        Glide.with(mContext).load(mBannerData?.displayImageUrl).into(binding.imgBanner)
    }

}



