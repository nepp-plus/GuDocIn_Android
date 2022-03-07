package com.neppplus.gudocin_android.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityEditShipmentInfoBinding

class EditShipmentInfoActivity : BaseActivity() {

    lateinit var binding: ActivityEditShipmentInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_shipment_info)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnSave.setOnClickListener {
            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("배송지 정보 저장")
            alert.setMessage("배송지 정보를 저장하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                val inputNickname = binding.edtShipmentNickname.text.toString()
                if (inputNickname.isEmpty()) {
                    Toast.makeText(mContext, "배송지 닉네임을 입력해 주세요", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                val inputShipment = binding.edtShipmentAddress.text.toString()
                if (inputShipment.isEmpty()) {
                    Toast.makeText(mContext, "배송지 주소를 입력해 주세요", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }

                val myIntent = Intent(mContext, PaymentActivity::class.java)
                myIntent.putExtra("nickname", inputNickname)
                myIntent.putExtra("shipment", inputShipment)
                startActivity(myIntent)
            })
            alert.setNegativeButton("취소", null)
            alert.show()
        }
    }

    override fun setValues() {
        btnExplore.visibility = View.GONE
        btnCart.visibility = View.GONE
    }

}