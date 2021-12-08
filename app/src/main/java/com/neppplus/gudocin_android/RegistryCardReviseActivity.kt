package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityRegistryCardReviseBinding

class RegistryCardReviseActivity : BaseActivity() {

    lateinit var binding: ActivityRegistryCardReviseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registry_card_revise)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        btnBack.setOnClickListener {

            val myIntent = Intent(mContext, RegistryCardListActivity::class.java)
            startActivity(myIntent)

            finish()

        }

    }

    override fun setValues() {
    }
}