package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityDeliveryInfoBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
//            발신/수신자가 다른 경우에는 어떻게 처리해야 하는지?
            val inputName = binding.edtConsumerName.text.toString()
            val inputPhone = binding.edtConsumerPhone.text.toString()

/* //           배송지는 어떻게 처리해야 하는지?
            val inputAddress = binding.edtConsumerAddress.text.toString() */

            apiService.patchRequestEditNickname("nickname", inputName).enqueue(object :
                Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!
                        Toast.makeText(mContext, "배달 정보가 저장되었습니다.", Toast.LENGTH_SHORT)
                            .show()

                        ContextUtil.setToken(mContext, basicResponse.data.token)
                        GlobalData.loginUser = basicResponse.data.user

                        val myIntent = Intent(mContext, PaymentActivity::class.java)
                        intent.putExtra("nickname", inputName)
                        startActivityForResult(myIntent, 4)
                        setResult(RESULT_OK)
                        finish()
                    } else {
                        val errorJson = JSONObject(response.errorBody()!!.string())
                        Log.d("에러경우", errorJson.toString())

                        val message = errorJson.getString("message")
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })

            apiService.patchRequestEditPhoneNumber("phone", inputPhone).enqueue(object :
                Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!
                        Toast.makeText(mContext, "배달 정보가 저장되었습니다.", Toast.LENGTH_SHORT)
                            .show()

                        ContextUtil.setToken(mContext, basicResponse.data.token)
                        GlobalData.loginUser = basicResponse.data.user

                        val myIntent = Intent(mContext, PaymentActivity::class.java)
                        intent.putExtra("phone", inputPhone)
                        startActivityForResult(myIntent, 5)
                        setResult(RESULT_OK)
                        finish()
                    } else {
                        val errorJson = JSONObject(response.errorBody()!!.string())
                        Log.d("에러경우", errorJson.toString())

                        val message = errorJson.getString("message")
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
        }
    }

    override fun setValues() {
        btnCart.visibility = View.GONE
    }

}