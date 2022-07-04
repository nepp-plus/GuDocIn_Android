package com.neppplus.gudocin_android.module

import android.content.Context
import com.google.gson.GsonBuilder
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.util.ContextUtil
import com.neppplus.gudocin_android.util.DateDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.gudoc.in"
    private var retrofit: Retrofit? = null

    @Singleton
    @Provides
    fun retrofitService(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun retrofitInstance(@ApplicationContext context: Context): Retrofit {
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
                .registerTypeAdapter(Date::class.java, DateDeserializer())
                .create() // Date 형태로 실제 파싱 진행 클래스 추가

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }
        return retrofit!!
    }

}