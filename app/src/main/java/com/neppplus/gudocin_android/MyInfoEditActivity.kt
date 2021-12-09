package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityMyInfoEditBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.utils.ContextUtil
import okhttp3.Request
import org.json.JSONObject
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


            val inputMyName = binding.editMyName.text.toString()


            apiService.patchRequestEditMyName(

                "nickname",
                inputMyName

//            서버에서 응답하기
            ).enqueue( object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {




                  if(response.isSuccessful){

                      val br = response.body()!!
                       Toast.makeText(mContext, "이름이 변경되었습니다.", Toast.LENGTH_SHORT).show()
                      ContextUtil.setToken(mContext, br.data.token)

                      GlobalData.loginUser = br.data.user

                  }
                    else{
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

        binding.txtMyEmail.text =GlobalData.loginUser!!.email

    }
}