package com.neppplus.gudocin_android.di

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.neppplus.gudocin_android.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
  override fun onCreate() {
    super.onCreate()
    KakaoSdk.init(this, getString(R.string.kakao_app_key))
  }
}