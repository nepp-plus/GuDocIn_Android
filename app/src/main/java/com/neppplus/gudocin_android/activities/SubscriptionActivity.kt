package com.neppplus.gudocin_android.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.SubscriptionViewPagerAdapter
import com.neppplus.gudocin_android.databinding.ActivitySubscriptionBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubscriptionActivity : BaseActivity() {

    lateinit var binding: ActivitySubscriptionBinding

    lateinit var mAdapterReview: SubscriptionViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subscription)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mAdapterReview = SubscriptionViewPagerAdapter(supportFragmentManager)
        binding.subscriptionViewPager.adapter = mAdapterReview
        binding.subscriptionTabLayout.setupWithViewPager(binding.subscriptionViewPager)

        getMyInfoFromServer()
    }

    fun getMyInfoFromServer() {
        apiService.getRequestMyInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}