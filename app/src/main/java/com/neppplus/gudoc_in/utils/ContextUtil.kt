package com.neppplus.gudoc_in.utils

import android.content.Context

class ContextUtil {

    companion object {

        // 어떤 파일? 파일 명
        private val prefName = "GuDocInPref"

        // 어떤 데이터 항목?  항목 명
        private val TOKEN = "TOKEN"
        private val DEVICE_TOKEN = "DEVICE_TOKEN"
        private val AUTO_LOGIN = "AUTO_LOGIN"

        // 저장 함수 (setter) / 조회 함수 (getter)
        fun setToken(context: Context, token: String) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(TOKEN, token).apply()
        }

        fun getToken(context: Context): String {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(TOKEN, "")!!
        }

        fun setDeviceToken(context: Context, dt: String) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(DEVICE_TOKEN, dt).apply()
        }

        fun getDeviceToken(context: Context): String {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(DEVICE_TOKEN, "")!!
        }

        fun setAutoLogin(context: Context, autoLogin: Boolean) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putBoolean(AUTO_LOGIN, autoLogin).apply()
        }

        fun getAutoLogin(context: Context): Boolean {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getBoolean(AUTO_LOGIN, false)
        }

    }

}