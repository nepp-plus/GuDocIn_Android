package com.neppplus.gudocin_android.ui.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.network.RetrofitServiceInstance

abstract class BaseFragment : Fragment() {

    lateinit var mContext: Context

    lateinit var apiService: RetrofitServiceInstance

    val isInitialized get() = this::apiService.isInitialized

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = requireContext()

        val retrofit = RetrofitService.getRetrofit(mContext)
        apiService = retrofit.create(RetrofitServiceInstance::class.java)
    }

    abstract fun setupEvents()
    abstract fun setValues()
}