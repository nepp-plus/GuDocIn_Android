package com.neppplus.gudocin_android

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.adapters.PurchaseViewPagerAdapter
import com.neppplus.gudocin_android.databinding.ActivityPurchaseListBinding
import com.neppplus.gudocin_android.datas.GlobalData

class PurchaseListActivity : BaseActivity() {

    lateinit var binding: ActivityPurchaseListBinding

    lateinit var mAdapter: PurchaseViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mAdapter = PurchaseViewPagerAdapter(supportFragmentManager)
        binding.purchaseViewPager.adapter = mAdapter
        binding.purchaseTabLayout.setupWithViewPager(binding.purchaseViewPager)
        binding.txtUserNickName.text = GlobalData.loginUser!!.nickname
    }

}