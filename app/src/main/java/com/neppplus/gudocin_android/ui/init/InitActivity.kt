package com.neppplus.gudocin_android.ui.init

import android.app.AlertDialog
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityInitBinding
import com.neppplus.gudocin_android.ui.base.renew.BaseActivity
import com.neppplus.gudocin_android.util.ContextUtil
import com.neppplus.gudocin_android.ui.login.LoginActivity
import com.neppplus.gudocin_android.ui.main.MainActivity
import com.neppplus.gudocin_android.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class InitActivity : BaseActivity<ActivityInitBinding, InitViewModel>(R.layout.activity_init) {

  private val initViewModel: InitViewModel by viewModels()

  override val getViewModel: InitViewModel
    get() = initViewModel

  var backKeyPressedTime: Long = 0

  var currentPosition = 0

  override fun initView() {
    binding {
      initViewPager()
      listenerTrigger()
    }
    observe()
    setFirebaseToken()
  }

  override fun observe() {
    super.observe()
    initViewModel.liveDataList.observe(this) {
      Log.d("Observe", it.toString())
    }
    initViewModel.getRequestInfo()
  }

  override fun onBackPressed() {
    if (System.currentTimeMillis() - backKeyPressedTime >= 1500) {
      backKeyPressedTime = System.currentTimeMillis()
      Toast.makeText(this, this.getString(R.string.back_pressed), Toast.LENGTH_SHORT).show()
    } else {
      ActivityCompat.finishAffinity(this)
      System.runFinalization()
      exitProcess(0)
    }
  }

  inner class PagerRunnable : Runnable {
    private val handler = Handler(Looper.getMainLooper()) {
      setPage()
      true
    }

    private fun setPage() {
      if (currentPosition == 4) currentPosition = 0
      binding.viewPager.setCurrentItem(currentPosition, true)
      currentPosition += 1
    }

    override fun run() {
      while (true) {
        Thread.sleep(1000)
        handler.sendEmptyMessage(0)
      }
    }
  }

  private fun ActivityInitBinding.initViewPager() {
    val adapter = InitViewPagerAdapter(this@InitActivity)
    viewPager.adapter = adapter

    val thread = Thread(PagerRunnable())
    thread.start()

    dotsIndicator.setViewPager(viewPager)
  }

  private fun setFirebaseToken() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener {
      if (it.isSuccessful) {
        val deviceToken = it.result
        Log.d(this.getString(R.string.fcm_token), deviceToken!!)
        ContextUtil.setDeviceToken(this, deviceToken)
      }
    }
  }

  private fun ActivityInitBinding.listenerTrigger() {
    val onClickListener = View.OnClickListener {
      when (it) {
        btnLogin -> loginListener()

        btnSignUp -> startActivity(Intent(this@InitActivity, SignUpActivity::class.java))

        btnExit -> exitListener()
      }
    }
    btnLogin.setOnClickListener(onClickListener)
    btnSignUp.setOnClickListener(onClickListener)
    btnExit.setOnClickListener(onClickListener)
  }

  private fun loginListener() {
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
      val myIntent: Intent = if (ContextUtil.getAutoLogin(this) && ContextUtil.getToken(this) != "") {
        Intent(this, MainActivity::class.java)
      } else {
        Intent(this, LoginActivity::class.java)
      }
      startActivity(myIntent)
    }, 1000)
  }

  private fun exitListener() {
    val alert = AlertDialog.Builder(this, R.style.DialogTheme)
    alert.setTitle(this.getString(R.string.exit_confirm))
    alert.setMessage(this.getString(R.string.do_you_wanna_exit))
    alert.setPositiveButton(resources.getString(R.string.confirm)) { _, _ ->
      finish()
    }
    alert.setNegativeButton(resources.getString(R.string.cancel), null)
    alert.show()
  }

}