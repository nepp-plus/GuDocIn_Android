package com.neppplus.gudoc_in.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudoc_in.R
import com.neppplus.gudoc_in.adapters.SubscriptionHistoryViewPagerAdapter
import com.neppplus.gudoc_in.databinding.ActivitySubscriptionHistoryBinding
import com.neppplus.gudoc_in.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubscriptionHistoryActivity : BaseActivity() {

    lateinit var binding: ActivitySubscriptionHistoryBinding

    lateinit var mAdapterReview: SubscriptionHistoryViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subscription_history)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mAdapterReview = SubscriptionHistoryViewPagerAdapter(supportFragmentManager)
        binding.activitiesHistoryViewPager.adapter = mAdapterReview
        binding.activitiesHistoryTabLayout.setupWithViewPager(binding.activitiesHistoryViewPager)

        getMyInfoFromServer()
    }

    fun getMyInfoFromServer() {
        apiService.getRequestMyInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
//                  Glide.with(mContext).load(br.data.user.profileImageURL).into(binding.imgProfile)
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}