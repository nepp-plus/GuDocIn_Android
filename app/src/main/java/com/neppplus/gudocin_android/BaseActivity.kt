package com.neppplus.gudocin_android

import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.neppplus.gudocin_android.api.ServerAPI
import com.neppplus.gudocin_android.api.ServerAPIInterface

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context

    lateinit var apiService: ServerAPIInterface

    var mActionBar= R.layout.custom_action_bar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        val retrofit = ServerAPI.getRetrofit(mContext)
        apiService = retrofit.create(ServerAPIInterface::class.java)

        mActionBar.cust


    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        actionBar.inflate(R.layout.custom_action_bar)
//    }

    abstract fun setupEvents()
    abstract fun setValues()

}