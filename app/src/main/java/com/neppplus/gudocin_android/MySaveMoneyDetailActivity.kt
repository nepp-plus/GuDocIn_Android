package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.adapters.SaveMoneyViewPagerAdapter
import com.neppplus.gudocin_android.databinding.ActivityMySaveMoneyDetailBinding

class MySaveMoneyDetailActivity : BaseActivity() {

    lateinit var binding : ActivityMySaveMoneyDetailBinding

    lateinit var mAdapter : SaveMoneyViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this, R.layout.activity_my_save_money_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mAdapter = SaveMoneyViewPagerAdapter(supportFragmentManager)
        binding.saveMondyViewPager.adapter = mAdapter

        binding.saveMoneyTabLayout.setupWithViewPager(binding.saveMondyViewPager)

    }
}