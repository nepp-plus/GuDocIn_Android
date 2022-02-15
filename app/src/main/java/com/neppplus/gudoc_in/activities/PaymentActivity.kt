package com.neppplus.gudoc_in.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.neppplus.gudoc_in.R
import com.neppplus.gudoc_in.databinding.ActivityPaymentBinding

class PaymentActivity : BaseActivity() {

    lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        setupEvents()
        setValues()

        binding.imgDropDownArrow.setOnClickListener {
            if (binding.layoutExpand.visibility == View.VISIBLE) {
                binding.layoutExpand.visibility = View.GONE
                binding.imgDropDownArrow.animate().setDuration(200).rotation(180f)
            } else {
                binding.layoutExpand.visibility = View.VISIBLE
                binding.imgDropDownArrow.animate().setDuration(200).rotation(0f)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 주문자 정보
        if (requestCode == 1 && resultCode == RESULT_OK && intent.hasExtra("nickname")) {
            binding.edtOrderName.setText(intent.getStringExtra("nickname"))
        } else if (requestCode == 2 && resultCode == RESULT_OK && intent.hasExtra("phone")) {
            binding.edtOrderPhone.setText(intent.getStringExtra("phone"))
        } else if (requestCode == 3 && resultCode == RESULT_OK && intent.hasExtra("email")) {
            binding.edtOrderEmail.setText(intent.getStringExtra("email"))
        } else {

        }
        // 수신자 정보
        if (requestCode == 4 && resultCode == RESULT_OK && intent.hasExtra("nickname")) {
            binding.edtReceiverName.setText(intent.getStringExtra("nickname"))
        } else if (requestCode == 5 && resultCode == RESULT_OK && intent.hasExtra("phone")) {
            binding.edtReceiverPhone.setText(intent.getStringExtra("phone"))
        } else {

        }
        // 카드 정보
        if (requestCode == 6 && resultCode == RESULT_OK && intent.hasExtra("nickname")) {
            binding.txtOrderCard.text = intent.getStringExtra("nickname")
        } else {

        }
    }

    override fun setupEvents() {
        btnBack.setOnClickListener {
            val myIntent = Intent(mContext, CartListActivity::class.java)
            startActivity(myIntent)
            finish()
        }
    }

    override fun setValues() {
        btnCart.visibility = View.GONE
    }

}