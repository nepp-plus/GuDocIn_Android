package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.adapters.BannerViewPagerAdapter
import com.neppplus.gudocin_android.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
//    이아현 메인작업

    lateinit var binding: ActivityMainBinding
    lateinit var mvpa : BannerViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mvpa = BannerViewPagerAdapter( supportFragmentManager )
        binding.mainBannerViewPager.adapter = mvpa


    }
}