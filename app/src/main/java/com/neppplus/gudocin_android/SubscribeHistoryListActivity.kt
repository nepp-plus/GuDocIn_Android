package com.neppplus.gudocin_android

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.adapters.SubscribeHistoryListViewPagerAdapter
import com.neppplus.gudocin_android.databinding.ActivitySubscribeHistoryListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubscribeHistoryListActivity : BaseActivity() {

    lateinit var binding: ActivitySubscribeHistoryListBinding

    lateinit var mAdapterReviewList: SubscribeHistoryListViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subscribe_history_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mAdapterReviewList = SubscribeHistoryListViewPagerAdapter(supportFragmentManager)
        binding.purchaseViewPager.adapter = mAdapterReviewList
        binding.purchaseTabLayout.setupWithViewPager(binding.purchaseViewPager)

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