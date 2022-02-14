package com.neppplus.gudoc_in.dummy

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudoc_in.R
import com.neppplus.gudoc_in.activities.BaseActivity
import com.neppplus.gudoc_in.databinding.ActivityDummyBinding

class DummyActivity : BaseActivity() {

    lateinit var binding: ActivityDummyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dummy)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }

}