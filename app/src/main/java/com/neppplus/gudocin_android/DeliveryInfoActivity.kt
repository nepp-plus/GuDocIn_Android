package com.neppplus.gudocin_android

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityDeliveryInfoBinding

class DeliveryInfoActivity : BaseActivity() {

    lateinit var binding: ActivityDeliveryInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_info)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        btnBack.setOnClickListener {

            val myIntent = Intent(mContext, PaymentActivity::class.java)
            startActivity(myIntent)

            finish()

        }

        binding.btnFindDelivery.setOnClickListener {

//            주소 관련 라이브러리?

        }

        binding.btnDeliveryInfoSave.setOnClickListener {

            val alert = AlertDialog.Builder(mContext, R.style.MyDialogTheme)

            alert.setTitle("배달 정보 변경")

            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                val myIntent = Intent(mContext, PaymentActivity::class.java)
                startActivity(myIntent)
            })
            alert.setNegativeButton("취소", null)

            alert.show()

        }

    }

    override fun setValues() {

        btnBasket.visibility = View.GONE
        btnBell.visibility = View.GONE

    }
}