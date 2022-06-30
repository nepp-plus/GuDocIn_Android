package com.neppplus.gudocin_android.application

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.neppplus.gudocin_android.R

class GlobalApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    KakaoSdk.init(this, getString(R.string.kakao_app_key))
  }
}