package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityMyInfoEditBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 회원정보 수정Actiivity BaseActivity 상속
class MyInfoEditActivity : BaseActivity() {

//    xml <layout> -> binding 변수생성 추후 사용
    lateinit var binding : ActivityMyInfoEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding 변수사용 xml화면 담아둠
             binding = DataBindingUtil.setContentView(this,R.layout.activity_my_info_edit)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        btnMyInfoSave 버튼을 클릭했을 경우
        binding.btnMyInfoSave.setOnClickListener {

//            inputMyname변수 안에 입력된 editMyName를 문자열로 변환하여 대입시킴
            val inputMyName = binding.editMyName.text.toString()

//           만들어둔 api서버호출
            apiService.patchRequestEditMyName(
//                필드의 닉네임을 inputMyName으로 바꿔라.
                "nickname",
                inputMyName

//            서버에서 응답하기
            ).enqueue( object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {


//                    입력값이 없으면 "변경할 이름을 입력하세요" 토스트 띄우기

                  if(inputMyName == null){
                       Toast.makeText(mContext, "변경할 이름을 입력하세요.", Toast.LENGTH_SHORT).show()
                   }
                    else{

//                       이상 없으면 "이름이 변경되었습니다" 토스트 띄우기
                       Toast.makeText(mContext, "이름이 변경 되었습니다", Toast.LENGTH_SHORT).show()



                   }


                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })
        }


        binding.txtIfEditEmail.setOnClickListener {

             val myIntent = Intent(mContext,EditMyEmailActivity::class.java)

            startActivity(myIntent)
        }

        binding.txtEdtMyPassWord.setOnClickListener {

            val myIntent = Intent(mContext,EditMyPassWordActivity::class.java)

            startActivity(myIntent)
        }

        binding.txtEditPhonNum.setOnClickListener {

            val myIntent = Intent(mContext,EditMyPhoneNumActivity::class.java)

            startActivity(myIntent)
        }


    }

    override fun setValues() {

    }
}