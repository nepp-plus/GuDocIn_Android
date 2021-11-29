package com.neppplus.gudocin_android.utils

import android.content.Context

class ContextUtil {

    companion object {

//        어떤 파일? 파일 명
        private val prefName = "GuDocInPref"

//        어떤 데이터 항목?  항목 명
        private val TOKEN = "TOKEN"
        private val DEVICE_TOKEN = "DEVICE_TOKEN"

//        저장 함수 (setter) / 조회 함수 (getter)

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

    }

}