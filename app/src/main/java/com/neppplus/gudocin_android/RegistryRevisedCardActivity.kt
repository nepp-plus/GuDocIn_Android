package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityRegistryRevisedCardBinding

class RegistryRevisedCardActivity : BaseActivity() {

    lateinit var binding: ActivityRegistryRevisedCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registry_revised_card)
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