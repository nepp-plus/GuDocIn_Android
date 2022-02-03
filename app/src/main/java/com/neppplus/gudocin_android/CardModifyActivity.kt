package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityCardModifyBinding

class CardModifyActivity : BaseActivity() {

    lateinit var binding: ActivityCardModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_card_modify)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        btnBack.setOnClickListener {
            val myIntent = Intent(mContext, CardManagementListActivity::class.java)
            startActivity(myIntent)
            finish()
        }
    }

    override fun setValues() {

    }

}