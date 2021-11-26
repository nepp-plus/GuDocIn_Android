package com.neppplus.gudocin_android.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerAPI {

    companion object {

        private var BASE_URL = "https://api.gudoc.in"

        private var retrofit: Retrofit? = null

        fun getRetrofit() : Retrofit {

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!

        }

    }



}