package com.neppplus.gudocin_android.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityEditEmailBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditEmailActivity : BaseActivity() {

    lateinit var binding: ActivityEditEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_email)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnMyInfoSave.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            val currentPassword = binding.edtCurrentPassword.text.toString()
            val inputPassword = binding.edtPassword.text.toString()
            val inputPhoneNum = binding.edtPhoneNum.text.toString()
            val inputNickname = binding.edtNickname.text.toString()

            apiService.patchRequestEditEmail(
                "receive_email", inputEmail
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        ContextUtil.setToken(mContext, br.data.token)
                        GlobalData.loginUser = br.data.user
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
            apiService.patchRequestEditPassword(
                "password", currentPassword, inputPassword
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        ContextUtil.setToken(mContext, br.data.token)
                        GlobalData.loginUser = br.data.user
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
            apiService.patchRequestEditPhoneNumber(
                "phone", inputPhoneNum
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        ContextUtil.setToken(mContext, br.data.token)
                        GlobalData.loginUser = br.data.user
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
            apiService.patchRequestEditNickname(
                "nickname", inputNickname,
            ).enqueue(
                object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            val br = response.body()!!
                            ContextUtil.setToken(mContext, br.data.token)
                            GlobalData.loginUser = br.data.user
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
            Toast.makeText(mContext, "회원정보가 수정되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun setValues() {

    }

}