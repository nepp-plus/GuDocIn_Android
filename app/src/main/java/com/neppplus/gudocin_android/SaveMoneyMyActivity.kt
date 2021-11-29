package com.neppplus.gudocin_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.MySavedmoneyBinding
import com.neppplus.gudocin_android.fragments.MyProfileFragment as MyProfileFragment1

class SaveMoneyMyActivity : BaseActivity() {

    lateinit var binding : MySavedmoneyBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.my_savedmoney)
        setValues()
        setupEvents()
    }
    override fun setupEvents() {






        }

    override fun setValues() {

    }

}