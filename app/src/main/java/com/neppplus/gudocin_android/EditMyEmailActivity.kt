package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityEditMyEmailBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMyEmailActivity : BaseActivity() {

    lateinit var binding: ActivityEditMyEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_edit_my_email)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.btnMyInfoSave.setOnClickListener {

            val inputEamil = binding.edtEmail.text.toString()

            apiService.patchRequestEditUserInfo(
                "receive_email",
                inputEamil
            ).enqueue( object : Callback<BasicResponse>{
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

    override fun setValues() {

    }
}