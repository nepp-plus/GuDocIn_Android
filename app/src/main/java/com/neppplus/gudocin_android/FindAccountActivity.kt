package com.neppplus.gudocin_android

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityFindAccountBinding

class FindAccountActivity : BaseActivity() {

    lateinit var binding: ActivityFindAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_account)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_account)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {
    }
}