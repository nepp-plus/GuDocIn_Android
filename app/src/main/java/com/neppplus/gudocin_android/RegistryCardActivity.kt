package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityRegistryCardBinding

class RegistryCardActivity : BaseActivity() {

    lateinit var binding: ActivityRegistryCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registry_card)
        setupEvents()
        setValues()

        binding.imgDropDownArrow.setOnClickListener {

            if (binding.layoutExpand1.visibility == View.VISIBLE) {
                binding.layoutExpand1.visibility = View.GONE
                binding.imgDropDownArrow.animate().setDuration(200).rotation(180f)
            } else {
                binding.layoutExpand1.visibility = View.VISIBLE
                binding.imgDropDownArrow.animate().setDuration(200).rotation(0f)
            }

        }

    }

    override fun setupEvents() {

        binding.btnCardInfoRevise.setOnClickListener {

            val myIntent = Intent(mContext, RegistryCardReviseActivity::class.java)
            startActivity(myIntent)

            finish()

        }

    }

    override fun setValues() {
    }
}