package com.neppplus.gudoc_in.application

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "c53922b4fc7f54934a3bca77009f4a04")
    }
}