package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.firebase.messaging.FirebaseMessaging
import com.neppplus.gudocin_android.databinding.ActivitySplashBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    fun setFirebaseToken() {

        FirebaseMessaging.getInstance().token.addOnCompleteListener {

//            토큰을 잘 받아왔다면
            if (it.isSuccessful) {

                val deviceToken = it.result

                Log.d("FCM토큰", deviceToken!!)
                ContextUtil.setDeviceToken(mContext, deviceToken)

                GlobalData.loginUser?.let {

                    apiService.patchRequestUpdateUserInfo("android_device_token", ContextUtil.getToken(mContext))
                }

            }

        }
    }

    override fun setValues() {

        setFirebaseToken()

        apiService.getRequestMyInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    GlobalData.loginUser = response.body()!!.data.user

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({

            val myIntent: Intent

            if (GlobalData.loginUser != null) {
                myIntent = Intent(mContext, NavigationActivity::class.java)
            } else {
                myIntent = Intent(mContext, LoginActivity::class.java)
            }

            startActivity(myIntent)

            finish()

        }, 2500)

    }
}