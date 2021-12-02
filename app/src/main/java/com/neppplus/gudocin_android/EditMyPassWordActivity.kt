package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityEditMyPassWordBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMyPassWordActivity : BaseActivity() {

    lateinit var binding: ActivityEditMyPassWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_my_pass_word)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnMyInfoSave.setOnClickListener {

            val inputPassword = binding.edtPassword.text.toString()


            val currentPaswword = binding.edtCurrentPassword.text.toString()

            apiService.patchRequestEditMyPassword(

                "password",
                inputPassword,
                currentPaswword

            ).enqueue(object :Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {


                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }


            })



            }

        }





    }

    override fun setValues() {

    }
}