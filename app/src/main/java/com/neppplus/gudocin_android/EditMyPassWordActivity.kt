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

        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnMyInfoSave.setOnClickListener {

        }

            val inputPassword = binding.edtPassword.toString()
            val inputPasswordRe = binding.edtReInputPassword.toString()


        if( inputPassword == inputPasswordRe) {

            apiService.patchRequestEditMyPassword(
                "password","inputPassword","",

            ).enqueue(object


        }



    }

    override fun setValues() {

    }
}