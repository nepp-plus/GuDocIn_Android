package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neppplus.gudocin_android.databinding.ActivityMyPurchaseListBinding

class MyPurchaseListActivity : BaseActivity() {

    lateinit var binding : ActivityMyPurchaseListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_purchase_list)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}