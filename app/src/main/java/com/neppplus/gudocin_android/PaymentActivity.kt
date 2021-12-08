package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityPaymentBinding
import com.neppplus.gudocin_android.datas.ReviewData

class PaymentActivity : BaseActivity() {

    lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //      주문자 정보
        if (requestCode == 1 && resultCode == RESULT_OK && intent.hasExtra("nickname")) {
            binding.txtConsumerName1.text = intent.getStringExtra("nickname")
        }

        else if (requestCode == 2 && resultCode == RESULT_OK && intent.hasExtra("phone")) {
            binding.txtConsumerPhone1.text = intent.getStringExtra("phone")
        }

        else if (requestCode == 3 && resultCode == RESULT_OK && intent.hasExtra("email")) {
            binding.txtConsumerEmail.text = intent.getStringExtra("email")
        }

        else {

        }

//      배달 정보
        if (requestCode == 4 && resultCode == RESULT_OK && intent.hasExtra("nickname")) {
            binding.txtConsumerName2.text = intent.getStringExtra("nickname")
        }

        else if (requestCode == 5 && resultCode == RESULT_OK && intent.hasExtra("phone")) {
            binding.txtConsumerPhone2.text = intent.getStringExtra("phone")
        }

        else {

        }

//      카드 정보
        if (requestCode == 6 && resultCode == RESULT_OK && intent.hasExtra("nickname")) {
            binding.txtConsumerCard.text = intent.getStringExtra("nickname")
        }

        else {

        }

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

        binding.btnRegistryCard.setOnClickListener {

            val myIntent = Intent(mContext, CardInfoActivity::class.java)
            startActivity(myIntent)

            finish()

        }

    }

    override fun setValues() {

//        mReviewData = intent.getSerializableExtra("review") as ReviewData?

        btnBasket.visibility = View.GONE
        btnBell.visibility = View.GONE

    }
}