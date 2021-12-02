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

class MyInfoEditActivity : BaseActivity() {

    lateinit var binding : ActivityMyInfoEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     binding = DataBindingUtil.setContentView(this,R.layout.activity_my_info_edit)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnMyInfoSave.setOnClickListener {

            val inputMyName = binding.editMyName.text.toString()
            apiService.patchRequestEditMyNumber(
                "nickname",
                inputMyName
            ).enqueue( object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                   if(inputMyName == null){
                       Toast.makeText(mContext, "변경할 이름을 입력하세요.", Toast.LENGTH_SHORT).show()
                   }
                    else{

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