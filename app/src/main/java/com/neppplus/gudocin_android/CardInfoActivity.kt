package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityCardInfoBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardInfoActivity : BaseActivity() {

    lateinit var binding: ActivityCardInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_card_info)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        btnBack.setOnClickListener {
            val myIntent = Intent(mContext, PaymentActivity::class.java)
            startActivity(myIntent)
            finish()
        }

        binding.btnCardInfoSave.setOnClickListener {

            val inputCardNickname = binding.edtCardNickName.text.toString()
            val inputCardNum = binding.edtCardNum.text.toString()
            val inputCardValidity = binding.edtCardValidity.text.toString()
            val inputCardBirthDay = binding.edtCardBirthDay.text.toString()
            val inputCardPassword = binding.edtCardPassword.text.toString()

            apiService.postRequestUserCard(
                inputCardNickname,
                inputCardNum,
                inputCardValidity,
                inputCardBirthDay,
                inputCardPassword
            ).enqueue(object :
                Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!
                        val userNickname = basicResponse.data.user.nickname
                        Toast.makeText(
                            mContext,
                            "${userNickname}님, 카드 등록이 완료되었습니다",
                            Toast.LENGTH_SHORT
                        ).show()

                        ContextUtil.setToken(mContext, basicResponse.data.token)
                        GlobalData.loginUser = basicResponse.data.user

                        val myIntent = Intent(mContext, PaymentActivity::class.java)
                        intent.putExtra("nickname", inputCardNickname)
                        startActivityForResult(myIntent, 6)
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
        btnBell.visibility = View.GONE
    }

}