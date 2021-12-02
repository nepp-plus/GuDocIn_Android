package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityMyInfoEditBinding

class MyInfoEditActivity : BaseActivity() {

    lateinit var binding : ActivityMyInfoEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     binding = DataBindingUtil.setContentView(this,R.layout.activity_my_info_edit)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {


        binding.txtIfEditEmail.setOnClickListener {

             val myIntent = Intent(mContext,EditMyEmailActivity::class.java)

            startActivity(myIntent)
        }

        binding.txtEdtMyPassWord.setOnClickListener {

            val myIntent = Intent(mContext,EditMyPassWordActivity::class.java)

            startActivity(myIntent)
        }

        binding.txtEditPhonNum.setOnClickListener {

            val myIntent = Intent(mContext,EditMyPhoneNumActivity::class.java)

            startActivity(myIntent)
        }


    }

    override fun setValues() {

    }
}