package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.adapters.PurchaseViewPagerAdapter
import com.neppplus.gudocin_android.databinding.ActivityMyPurchaseListBinding

class MyPurchaseListActivity : BaseActivity() {

    lateinit var binding : ActivityMyPurchaseListBinding

    lateinit var mAdapter: PurchaseViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_my_purchase_list)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mAdapter = PurchaseViewPagerAdapter(supportFragmentManager)
        binding.purchaseViewPager.adapter = mAdapter

    }
}