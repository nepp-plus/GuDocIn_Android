package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityCustomerCenterBinding


class CustomerCenter : BaseActivity() {

    lateinit var binding : ActivityCustomerCenterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_customer_center)
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}