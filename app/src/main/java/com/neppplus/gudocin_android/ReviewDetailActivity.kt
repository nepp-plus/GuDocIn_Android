package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityReviewDetailBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewDetailActivity : BaseActivity() {

    lateinit var binding: ActivityReviewDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_review_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        apiService.getRequestReviewList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }


        })

    }
}