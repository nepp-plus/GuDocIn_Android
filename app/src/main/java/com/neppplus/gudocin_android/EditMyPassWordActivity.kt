package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityEditMyPassWordBinding

class EditMyPassWordActivity : BaseActivity() {

    lateinit var binding : ActivityEditMyPassWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_my_pass_word)
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}