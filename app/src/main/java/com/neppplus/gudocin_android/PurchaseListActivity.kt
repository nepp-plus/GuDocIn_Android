package com.neppplus.gudocin_android

import android.os.Bundle
import com.neppplus.gudocin_android.databinding.ActivityPurchaseListBinding

class PurchaseListActivity : BaseActivity() {

    lateinit var binding: ActivityPurchaseListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }

}