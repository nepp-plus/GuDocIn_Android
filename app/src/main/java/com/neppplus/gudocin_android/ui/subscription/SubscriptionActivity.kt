package com.neppplus.gudocin_android.ui.subscription

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivitySubscriptionBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.user.GlobalData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseActivity
import com.neppplus.gudocin_android.ui.review.subscription.SubscriptionViewPagerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubscriptionActivity : BaseActivity() {

    lateinit var binding: ActivitySubscriptionBinding

    lateinit var retrofitService: RetrofitService

    private lateinit var mSubscriptionViewPagerAdapter: SubscriptionViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subscription)
        binding.apply {
            activity = this@SubscriptionActivity
            retrofitService =
                Retrofit.getRetrofit(this@SubscriptionActivity).create(RetrofitService::class.java)
            initView()
        }
    }

    private fun ActivitySubscriptionBinding.initView() {
        mSubscriptionViewPagerAdapter =
            SubscriptionViewPagerAdapter(supportFragmentManager, this@SubscriptionActivity)

        vpSubscription.adapter = mSubscriptionViewPagerAdapter
        tlSubscription.setupWithViewPager(binding.vpSubscription)

        getRequestInfo()
        loginUserProvider()

        actionBarVisibility()
    }

    private fun actionBarVisibility() {
        shopping.visibility = View.GONE
        cart.visibility = View.GONE
    }

    private fun ActivitySubscriptionBinding.loginUserProvider() {
        when (GlobalData.loginUser!!.provider) {
            "kakao" -> {
                imgProvider.apply {
                    setImageResource(R.drawable.kakao_logo)
                    visibility = View.VISIBLE
                }
            }

            "facebook" -> {
                imgProvider.apply {
                    setImageResource(R.drawable.facebook_logo)
                    visibility = View.VISIBLE
                }
            }

            "google" -> {
                imgProvider.apply {
                    setImageResource(R.drawable.google_logo)
                    visibility = View.VISIBLE
                }
            }

            else -> {
                imgProvider.visibility = View.GONE
            }
        }
    }

    private fun ActivitySubscriptionBinding.getRequestInfo() {
        retrofitService.getRequestInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val basicResponse = response.body()!!
                    txtNickname.text = basicResponse.data.user.nickname
                    Glide.with(this@SubscriptionActivity)
                        .load(basicResponse.data.user.profileImageURL).into(imgProfile)
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
        })
    }

}