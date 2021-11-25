package com.neppplus.gudocin_android

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neppplus.gudocin_android.api.ServerAPI
import com.neppplus.gudocin_android.api.ServerAPIInterface

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context

    lateinit var apiService: ServerAPIInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        val retrofit = ServerAPI.getRetrofit()
        apiService = retrofit.create(ServerAPIInterface::class.java)

    }

    abstract fun setupEvents()
    abstract fun setValues()

}