package com.neppplus.gudocin_android.ui.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentBannerBinding
import com.neppplus.gudocin_android.model.banner.BannerData
import com.neppplus.gudocin_android.ui.base.renew.BaseFragment

class BannerFragment(private val mBannerData: BannerData? = null) : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding<FragmentBannerBinding>(inflater, R.layout.fragment_banner, container).apply {
            fragment = this@BannerFragment
            initView()
        }.root
    }

    private fun FragmentBannerBinding.initView() {
        Glide.with(requireContext()).load(mBannerData?.displayImageUrl).into(imgBanner)
    }
}