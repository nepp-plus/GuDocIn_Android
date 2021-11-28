package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivitySignUpBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    var isDuplOk = false
    var isPasswordLengthOk = false
    var isPasswordSame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.edtPassword.addTextChangedListener {

            if (it.toString().length >= 8) {
                binding.txtPasswordCheckResult1.text = "사용해도 좋은 비밀번호입니다."
                isPasswordLengthOk = true
            } else {
                binding.txtPasswordCheckResult1.text = "8글자 이상으로 해주세요."
                isPasswordLengthOk = false
            }

            isPasswordSame = compareTwoPasswords()

        }

        binding.edtPasswordConfirm.addTextChangedListener {

            isPasswordSame = compareTwoPasswords()

        }

        binding.edtEmail.addTextChangedListener {

            binding.txtNicknameCheckResult.text = "닉네임 중복검사를 해주세요."
            isDuplOk = false

        }

        binding.btnNicknameCheck.setOnClickListener {

            val nickname = binding.edtNickname.text.toString()

            apiService.getRequestDuplicatedCheck("NICK_NAME", nickname)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {

                        if (response.isSuccessful) {

                            binding.txtNicknameCheckResult.text = "사용해도 좋은 닉네임 입니다."
                            isDuplOk = true

                        } else {

                            binding.txtNicknameCheckResult.text = "다른 닉네임으로 다시 검사해주세요."
                            isDuplOk = false

                        }

                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }

                })

        }

        binding.btnSignUp.setOnClickListener {

            if (!isDuplOk) {
                Toast.makeText(mContext, "닉네임 중복검사를 해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isPasswordLengthOk) {
                Toast.makeText(mContext, "비밀번호는 8글자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (!isPasswordSame) {
                Toast.makeText(mContext, "두개의 비밀번호는 서로 같아야합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val nickname = binding.edtNickname.text.toString()

            apiService.putRequestSignUp(email, password, nickname).enqueue(object :
                Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    if (response.isSuccessful) {

                        val br = response.body()!!

                        Log.d("가입한 사람 토큰", br.data.token)

                        val signUpUserNickname = br.data.user.nickname
                        Toast.makeText(
                            mContext,
                            "${signUpUserNickname}님 가입을 축하합니다!",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()

                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

    }

    fun compareTwoPasswords(): Boolean {

        val originalPassword = binding.edtPassword.text.toString()
        val confirmPassword = binding.edtPasswordConfirm.text.toString()

        if (originalPassword == confirmPassword) {
            binding.txtPasswordCheckResult2.text = "사용해도 좋습니다."
            return true
        } else {
            binding.txtPasswordCheckResult2.text = "위의 비밀번호와 일치해야 합니다."
            return false
        }

    }

    override fun setValues() {

    }

}