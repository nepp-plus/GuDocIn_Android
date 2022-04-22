package com.neppplus.gudocin_android.ui.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.google.firebase.messaging.FirebaseMessaging
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.ui.adapter.StartViewPagerAdapter
import com.neppplus.gudocin_android.databinding.ActivityStartBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.GlobalData
import com.neppplus.gudocin_android.utils.ContextUtil
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartActivity : BaseActivity() {

    lateinit var binding: ActivityStartBinding

    internal lateinit var viewPager: ViewPager

    var backKeyPressedTime: Long = 0

    var currentPosition = 0

    val handler = Handler(Looper.getMainLooper()) {
        setPage()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start)
        setupEvents()
        setValues()

        viewPager = findViewById(R.id.viewPager) as ViewPager

        val adapter = StartViewPagerAdapter(this)
        viewPager.adapter = adapter

        val thread = Thread(PagerRunnable())
        thread.start()

        val dotsIndicator = findViewById<DotsIndicator>(R.id.dotsIndicator)
        dotsIndicator.setViewPager(viewPager)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backKeyPressedTime >= 1500) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.finishAffinity(this)
            System.runFinalization()
            System.exit(0)
        }
    }

    fun setPage() {
        if (currentPosition == 4) currentPosition = 0
        binding.viewPager.setCurrentItem(currentPosition, true)
        currentPosition += 1
    }

    inner class PagerRunnable : Runnable {
        override fun run() {
            while (true) {
                Thread.sleep(1000)
                handler.sendEmptyMessage(0)
            }
        }
    }

    fun setFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                val deviceToken = it.result
                Log.d("FCM토큰", deviceToken!!)
                ContextUtil.setDeviceToken(mContext, deviceToken)

                GlobalData.loginUser.let {
                    apiService.patchRequestUpdateUserInfo(
                        "android_device_token",
                        ContextUtil.getDeviceToken(mContext)
                    )
                }
            }
        }
    }

    override fun setupEvents() {
        binding.btnLogin.setOnClickListener {
            val myHandler = Handler(Looper.getMainLooper())
            myHandler.postDelayed({
                val myIntent: Intent
                if (ContextUtil.getAutoLogin(mContext) && ContextUtil.getToken(mContext) != "") {
                    myIntent = Intent(mContext, MainActivity::class.java)
                } else {
                    myIntent = Intent(mContext, LoginActivity::class.java)
                }
                startActivity(myIntent)
                finish()
            }, 1000)
        }

        binding.btnSignUp.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }

        binding.btnExit.setOnClickListener {
            val alert = AlertDialog.Builder(mContext, R.style.DialogTheme)
            alert.setTitle("나가기 확인")
            alert.setMessage("정말 나가시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                finish()
            })
            alert.setNegativeButton("취소", null)
            alert.show()
        }
    }

    override fun setValues() {
        setFirebaseToken()

        apiService.getRequestInfo().enqueue(object : Callback<BasicResponse> {
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