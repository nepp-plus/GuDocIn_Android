package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.BannerViewPagerAdapter
import com.neppplus.gudocin_android.databinding.BannerItemForMainBinding

class HomeFragment : BaseFragment() {

    val handler=Handler(Looper.getMainLooper()){
        true
    }

    lateinit var binding: BannerItemForMainBinding

    lateinit var mvpa: BannerViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mvpa = BannerViewPagerAdapter(childFragmentManager)
        binding.mainBannerViewPager.a


    }

}