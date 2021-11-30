package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityUserEditBinding

class UserEditActivity : BaseActivity() {

    lateinit var binding : ActivityUserEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_user_edit)

        setValues()
        setupEvents()
    }



    override fun setupEvents() {

    }

    override fun setValues() {

    }
}