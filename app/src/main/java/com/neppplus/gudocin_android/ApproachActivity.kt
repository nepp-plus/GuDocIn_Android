package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.neppplus.gudocin_android.adapters.ApproachViewPagerAdapter
import com.neppplus.gudocin_android.databinding.ActivityApproachBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApproachActivity : BaseActivity() {

    lateinit var binding: ActivityApproachBinding

    internal lateinit var viewPager: ViewPager

    var currentPosition = 0

    val handler = Handler(Looper.getMainLooper()) {
        setPage()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_approach)
        setupEvents()
        setValues()

        viewPager = findViewById(R.id.viewPager) as ViewPager

        val adapter = ApproachViewPagerAdapter(this)
        viewPager.adapter = adapter

        val thread = Thread(PagerRunnable())
        thread.start()

    }

    fun setPage() {

        if (currentPosition == 3) currentPosition = 0
        viewPager.setCurrentItem(currentPosition, true)
        currentPosition += 1

    }

    inner class PagerRunnable : Runnable {
        override fun run() {
            while (true) {
                Thread.sleep(3000)
                handler.sendEmptyMessage(0)
            }
        }
    }

    override fun setupEvents() {

        binding.btnLogin.setOnClickListener {

            val myIntent = Intent(mContext, LoginActivity::class.java)
            startActivity(myIntent)

        }

        binding.btnSignUp.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)

        }

    }

    override fun setValues() {

        apiService.getRequestMyInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    GlobalData.loginUser = response.body()!!.data.user

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

}