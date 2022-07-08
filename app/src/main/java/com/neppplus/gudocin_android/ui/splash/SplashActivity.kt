package com.neppplus.gudocin_android.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivitySplashBinding
import com.neppplus.gudocin_android.ui.base.BaseActivity
import com.neppplus.gudocin_android.ui.init.InitActivity

class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        initView()
    }

    private fun initView() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, InitActivity::class.java))
            finish()
        }, 2500)
    }

}