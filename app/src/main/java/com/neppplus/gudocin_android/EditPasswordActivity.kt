package com.neppplus.gudocin_android

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityEditPasswordBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPasswordActivity : BaseActivity() {

    lateinit var binding: ActivityEditPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_password)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnMyInfoSave.setOnClickListener {
            val inputPassword = binding.edtPassword.text.toString()
            val currentPassword = binding.edtCurrentPassword.text.toString()

//            만들어둔 api 서버 접속
            apiService.patchRequestEditMyPassword(
//              변경할 필드 password 로, 변경할 비밀번호와 현재 비밀번호 입력
                "password",
                inputPassword,
                currentPassword
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
//                                비밀번호 변경 성공 토스트
                        Toast.makeText(mContext, "비밀번호가 변경 되었습니다", Toast.LENGTH_SHORT).show()

//                        새로 내려오는 토큰값 추출 -> ContextUtil 의 기능 이용 새로 저장
//                        기본 응답 파싱 -> 변수에 저장
                        val br = response.body()!!
                        val token = br.data.token
                        ContextUtil.setToken(mContext, token)
                    } else {
//                        에러 경우 (비번 병경 실패) => 왜? 실패?
//                        실패는 자동 파싱X => BasicResponse 사용 불가
//                          JSONObj 생성 파싱
                        val jsonObj = JSONObject(response.errorBody()!!.string())
                        val message = jsonObj.getString("message")
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
