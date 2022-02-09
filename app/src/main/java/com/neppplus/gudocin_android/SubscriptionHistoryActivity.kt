package com.neppplus.gudocin_android

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.adapters.SubscriptionHistoryViewPagerAdapter
import com.neppplus.gudocin_android.databinding.ActivitySubscriptionHistoryBinding
import com.neppplus.gudocin_android.datas.BasicResponse
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
        binding.subscribeHistoryViewPager.adapter = mAdapterReview
        binding.subscribeHistoryTabLayout.setupWithViewPager(binding.subscribeHistoryViewPager)

        getMyInfoFromServer()
    }

    fun getMyInfoFromServer() {
        apiService.getRequestMyInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    Glide.with(mContext).load(br.data.user.profileImageURL).into(binding.imgProfile)
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}