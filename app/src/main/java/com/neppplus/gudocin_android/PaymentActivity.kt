package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityPaymentBinding

class PaymentActivity : BaseActivity() {

    lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        btnBack.setOnClickListener {

            val myIntent = Intent(mContext, BasketListActivity::class.java)
            startActivity(myIntent)

            finish()

        }

        binding.btnRevise1.setOnClickListener {

            val myIntent = Intent(mContext, ConsumerInfoActivity::class.java)
            startActivity(myIntent)

            finish()

        }

        binding.btnRevise2.setOnClickListener {

            val myIntent = Intent(mContext, DeliveryInfoActivity::class.java)
            startActivity(myIntent)

            finish()

        }


    }

    override fun setValues() {

        btnBasket.visibility = View.GONE
        btnBell.visibility = View.GONE

    }
}