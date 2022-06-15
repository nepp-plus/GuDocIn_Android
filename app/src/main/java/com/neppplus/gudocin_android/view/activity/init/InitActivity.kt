package com.neppplus.gudocin_android.view.activity.init

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
import com.neppplus.gudocin_android.databinding.ActivityInitBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.GlobalData
import com.neppplus.gudocin_android.util.context.ContextUtil
import com.neppplus.gudocin_android.view.adapter.init.InitViewPagerAdapter
import com.neppplus.gudocin_android.view.activity.BaseActivity
import com.neppplus.gudocin_android.view.activity.login.LoginActivity
import com.neppplus.gudocin_android.view.activity.main.MainActivity
import com.neppplus.gudocin_android.view.activity.signup.SignUpActivity
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

class InitActivity : BaseActivity() {

  lateinit var binding: ActivityInitBinding

  internal lateinit var viewPager: ViewPager

  var backKeyPressedTime: Long = 0

  var currentPosition = 0

  val handler = Handler(Looper.getMainLooper()) {
    setPage()
    true
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_init)
    setupEvents()
    setValues()

    viewPager = findViewById(R.id.viewPager)

    val adapter = InitViewPagerAdapter(this)
    viewPager.adapter = adapter

    val thread = Thread(PagerRunnable())
    thread.start()

    val dotsIndicator = findViewById<DotsIndicator>(R.id.dotsIndicator)
    dotsIndicator.setViewPager(viewPager)
  }

  override fun onBackPressed() {
    if (System.currentTimeMillis() - backKeyPressedTime >= 1500) {
      backKeyPressedTime = System.currentTimeMillis()
      Toast.makeText(this, mContext.getString(R.string.back_pressed), Toast.LENGTH_SHORT).show()
    } else {
      ActivityCompat.finishAffinity(this)
      System.runFinalization()
      exitProcess(0)
    }
  }

  private fun setPage() {
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

  private fun setFirebaseToken() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener {
      if (it.isSuccessful) {
        val deviceToken = it.result
        Log.d(mContext.getString(R.string.fcm_token), deviceToken!!)
        ContextUtil.setDeviceToken(mContext, deviceToken)

        GlobalData.loginUser.let {
          apiService.patchRequestUpdateUserInfo("android_device_token", ContextUtil.getDeviceToken(mContext))
        }
      }
    }
  }

  override fun setupEvents() {
    binding.btnLogin.setOnClickListener {
      val myHandler = Handler(Looper.getMainLooper())
      myHandler.postDelayed({
        val myIntent: Intent = if (ContextUtil.getAutoLogin(mContext) && ContextUtil.getToken(mContext) != "") {
          Intent(mContext, MainActivity::class.java)
        } else {
          Intent(mContext, LoginActivity::class.java)
        }
        startActivity(myIntent)
      }, 1000)
    }

    binding.btnSignUp.setOnClickListener {
      val myIntent = Intent(mContext, SignUpActivity::class.java)
      startActivity(myIntent)
    }

    binding.btnExit.setOnClickListener {
      val alert = AlertDialog.Builder(mContext, R.style.DialogTheme)
      alert.setTitle(mContext.getString(R.string.exit_confirm))
      alert.setMessage(mContext.getString(R.string.do_you_wanna_exit))
      alert.setPositiveButton(resources.getString(R.string.confirm), DialogInterface.OnClickListener { _, _ ->
        finish()
      })
      alert.setNegativeButton(resources.getString(R.string.cancel), null)
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

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
    })
  }

}