package com.neppplus.gudocin_android

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityCustomerCenterBinding


class CustomerCenterActivity : BaseActivity() {

    lateinit var binding : ActivityCustomerCenterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_customer_center)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {
        binding.btnDial.setOnClickListener {
        //            입력한 전화번호? 추출 (변수에 저장)

//            val inputPhoneNum =  txtCustomerNum.text.toString()

//            그 전화번호에 실제 전화 연결 (DIAL)

            val myUri = Uri.parse("tel:15882288")
            val myIntent = Intent( Intent.ACTION_DIAL,  myUri )
            startActivity(myIntent)

        }

    }

    override fun setValues() {

    }
}