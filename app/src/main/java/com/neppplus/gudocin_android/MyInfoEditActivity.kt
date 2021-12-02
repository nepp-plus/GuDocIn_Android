package com.neppplus.gudocin_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neppplus.gudocin_android.databinding.ActivityMyInfoEditBinding
import com.neppplus.gudocin_android.fragments.EditMyEmailFragment

class MyInfoEditActivity : BaseActivity() {

    lateinit var binding : ActivityMyInfoEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_info_edit)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.txtIfEditEmail.setOnClickListener {

            val myIntent = Intent(mContext,EditMyEmailFragment::class.java)

            startActivity(myIntent)
        }

    }

    override fun setValues() {

    }
}