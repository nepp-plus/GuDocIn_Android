package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityEditMyInfoBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMyInfoActivity : BaseActivity() {

    lateinit var binding: ActivityEditMyInfoBinding

    var isPasswordLengthOk = false
    var isDuplicatedOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_my_info)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.edtPassword.addTextChangedListener {
            if (it.toString().length >= 8) {
                binding.txtPasswordCheck.text = "사용해도 좋은 비밀번호입니다"
                isPasswordLengthOk = true
            } else {
                binding.txtPasswordCheck.text = "비밀번호는 8글자 이상이어야 합니다"
                isPasswordLengthOk = false
            }
        }

        binding.edtNicknameCheck.addTextChangedListener {
            binding.txtNicknameCheck.text = "닉네임 중복확인을 진행해야 합니다"
            isDuplicatedOk = false
        }

        binding.btnNicknameCheck.setOnClickListener {
            val nickname = binding.edtNicknameCheck.text.toString()
            apiService.getRequestDuplicatedCheck("NICK_NAME", nickname)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            binding.txtNicknameCheck.text = "사용해도 좋은 닉네임입니다"
                            isDuplicatedOk = true
                        } else {
                            binding.txtNicknameCheck.text = "중복된 닉네임이 존재합니다"
                            isDuplicatedOk = false
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
        }

        binding.btnEmailChange.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            apiService.patchRequestEditEmail(
                "receive_email", inputEmail
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        Toast.makeText(
                            mContext,
                            "이메일이 수정되었습니다",
                            Toast.LENGTH_SHORT
                        ).show()

                        ContextUtil.setToken(mContext, br.data.token)
                        GlobalData.loginUser = br.data.user

                        val myIntent = Intent(mContext, NavigationActivity::class.java)
                        startActivity(myIntent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
        }

        binding.btnPasswordChange.setOnClickListener {

            if (!isPasswordLengthOk) {
                Toast.makeText(mContext, "비밀번호는 8글자 이상이어야 합니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputPassword = binding.edtPassword.text.toString()
            val currentPassword = binding.edtCurrentPassword.text.toString()

            apiService.patchRequestEditPassword(
                "password", inputPassword, currentPassword
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        Toast.makeText(
                            mContext,
                            "비밀번호가 수정되었습니다",
                            Toast.LENGTH_SHORT
                        ).show()

                        ContextUtil.setToken(mContext, br.data.token)
                        GlobalData.loginUser = br.data.user

                        val myIntent = Intent(mContext, NavigationActivity::class.java)
                        startActivity(myIntent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
        }

        binding.btnPhoneNumChange.setOnClickListener {
            val inputPhoneNumber = binding.edtPhoneNum.text.toString()
            apiService.patchRequestEditPhoneNumber(
                "phone", inputPhoneNumber,
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        Toast.makeText(
                            mContext,
                            "전화번호가 수정되었습니다",
                            Toast.LENGTH_SHORT
                        ).show()

                        ContextUtil.setToken(mContext, br.data.token)
                        GlobalData.loginUser = br.data.user

                        val myIntent = Intent(mContext, NavigationActivity::class.java)
                        startActivity(myIntent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
        }

        binding.btnNicknameChange.setOnClickListener {

            if (!isDuplicatedOk) {
                Toast.makeText(mContext, "중복된 닉네임이 존재합니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputNickname = binding.edtNickname.text.toString()
            apiService.patchRequestEditNickname(
                "nickname", inputNickname,
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        Toast.makeText(
                            mContext,
                            "닉네임이 수정되었습니다",
                            Toast.LENGTH_SHORT
                        ).show()

                        ContextUtil.setToken(mContext, br.data.token)
                        GlobalData.loginUser = br.data.user

                        val myIntent = Intent(mContext, NavigationActivity::class.java)
                        startActivity(myIntent)
                        finish()
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