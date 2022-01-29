package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityFindAccountBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindAccountActivity : BaseActivity() {

    lateinit var binding: ActivityFindAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_account)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_account)
        setupEvents()
        setValues()
    }

    override fun onBackPressed() {
        val myIntent = Intent(mContext, LoginActivity::class.java)
        startActivity(myIntent)
        finish()
    }

    override fun setupEvents() {
        binding.btnSubmit1.setOnClickListener {
            val inputNickname = binding.edtNickname1.text.toString()
            val inputPhone = binding.edtPhone.text.toString()

            apiService.getRequestEmail(inputNickname, inputPhone).enqueue(object :
                Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(mContext, "등록된 연락처로 가입한 이메일을 보냈습니다.", Toast.LENGTH_SHORT)
                            .show()
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

        binding.btnSubmit2.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            val inputNickname = binding.edtNickname2.text.toString()

            apiService.postRequestPassword(inputEmail, inputNickname)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(mContext, "임시 비밀번호를 메일로 전송했습니다.", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            val errorJson = JSONObject(response.errorBody()!!.string())
                            Log.d("에러 경우", errorJson.toString())

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

    }

}