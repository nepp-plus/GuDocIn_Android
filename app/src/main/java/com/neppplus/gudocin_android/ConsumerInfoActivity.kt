package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityConsumerInfoBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerInfoActivity : BaseActivity() {

    lateinit var binding: ActivityConsumerInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_consumer_info)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        btnBack.setOnClickListener {
            val myIntent = Intent(mContext, PaymentActivity::class.java)
            startActivity(myIntent)
            finish()
        }

        binding.btnConsumerInfoSave.setOnClickListener {

            val inputName = binding.edtConsumerName.text.toString()
            val inputPhone = binding.edtConsumerPhone.text.toString()
            val inputEmail = binding.edtConsumerEmail.text.toString()

            apiService.patchRequestEditMyName("nickname", inputName).enqueue(object :
                Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!
                        Toast.makeText(mContext, "주문자 정보가 저장되었습니다.", Toast.LENGTH_SHORT)
                            .show()

                        ContextUtil.setToken(mContext, basicResponse.data.token)
                        GlobalData.loginUser = basicResponse.data.user

                        val myIntent = Intent(mContext, PaymentActivity::class.java)
                        intent.putExtra("nickname", inputName)
                        startActivityForResult(myIntent, 1)
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

            apiService.patchRequestEditMyNumber("phone", inputPhone).enqueue(object :
                Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!
                        Toast.makeText(mContext, "주문자 정보가 저장되었습니다.", Toast.LENGTH_SHORT)
                            .show()

                        ContextUtil.setToken(mContext, basicResponse.data.token)
                        GlobalData.loginUser = basicResponse.data.user

                        val myIntent = Intent(mContext, PaymentActivity::class.java)
                        intent.putExtra("phone", inputPhone)
                        startActivityForResult(myIntent, 2)
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

            apiService.patchRequestEditMyEmail("email", inputEmail).enqueue(object :
                Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!
                        Toast.makeText(mContext, "주문자 정보가 저장되었습니다.", Toast.LENGTH_SHORT)
                            .show()

                        ContextUtil.setToken(mContext, basicResponse.data.token)
                        GlobalData.loginUser = basicResponse.data.user

                        val myIntent = Intent(mContext, PaymentActivity::class.java)
                        intent.putExtra("email", inputEmail)
                        startActivityForResult(myIntent, 3)
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
        btnBasket.visibility = View.GONE
    }

}