package com.neppplus.gudocin_android

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityEditEmailBinding
import com.neppplus.gudocin_android.datas.BasicResponse
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
            apiService.patchRequestEditMyEmail(
                "receive_email",
                inputEmail
            ).enqueue(object : Callback<BasicResponse> {
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