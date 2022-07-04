package com.neppplus.gudocin_android.ui.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService

abstract class BaseFragment : Fragment() {

    lateinit var mContext: Context

    lateinit var apiService: RetrofitService

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = requireContext()

        val retrofit = Retrofit.getRetrofit(mContext)
        apiService = retrofit.create(RetrofitService::class.java)
    }

    abstract fun setupEvents()
    abstract fun setValues()

}