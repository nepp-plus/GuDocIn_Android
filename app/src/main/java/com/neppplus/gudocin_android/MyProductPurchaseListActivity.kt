package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityMyProductPurchaseListBinding

class MyProductPurchaseListActivity : BaseActivity() {

    lateinit var binding : ActivityMyProductPurchaseListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_my_product_purchase_list)
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}