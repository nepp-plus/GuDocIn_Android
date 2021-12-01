package com.neppplus.gudocin_android

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivitySaveMonyMyBinding

class SaveMonyMyActivity : BaseActivity() {

    lateinit var binding : ActivitySaveMonyMyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding= DataBindingUtil.setContentView(this,R.layout.activity_save_mony_my)

        setValues()
        setupEvents()
    }





    override fun setupEvents() {

    }

    override fun setValues() {

    }
}