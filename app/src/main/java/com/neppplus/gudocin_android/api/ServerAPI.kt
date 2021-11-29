package com.neppplus.gudocin_android.api

import android.content.Context
import com.neppplus.gudocin_android.utils.ContextUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerAPI {

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

                val myClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(myClient)
                    .build()
            }

            return retrofit!!

        }

    }

}