package com.neppplus.gudocin_android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "e01a6443a1c0ffc7bc1e1eb6633b56e2")

    }
}