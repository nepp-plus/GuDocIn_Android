package com.neppplus.gudocin_android.network

import android.content.Context
import com.google.gson.GsonBuilder
import com.neppplus.gudocin_android.util.ContextUtil
import com.neppplus.gudocin_android.util.DateDeserializer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class Retrofit {
    companion object {
        private var BASE_URL = "https://api.gudoc.in"
        private var retrofit: Retrofit? = null

        fun getRetrofit(context: Context): Retrofit {
            if (retrofit == null) {
                val interceptor = Interceptor {
                    with(it) {
                        val newRequest = request().newBuilder()
                            .addHeader("X-Http-Token", ContextUtil.getToken(context))
                            .build()
                        proceed(newRequest)
                    }
                }

                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter(
                        Date::class.java,
                        DateDeserializer()
                    )  // Date 형태 파싱 진행 클래스 추가
                    .create()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build()
            }
            return retrofit!!
        }

    }
}