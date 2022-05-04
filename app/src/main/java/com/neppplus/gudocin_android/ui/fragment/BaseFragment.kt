package com.neppplus.gudocin_android.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.neppplus.gudocin_android.api.ServerAPI
import com.neppplus.gudocin_android.api.ServerAPIInterface

abstract class BaseFragment : Fragment() {

    lateinit var mContext: Context

    lateinit var apiService: ServerAPIInterface

    val isInitialized get() = this::apiService.isInitialized

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = requireContext()

        val retrofit = ServerAPI.getRetrofit(mContext)
        apiService = retrofit.create(ServerAPIInterface::class.java)
    }

    abstract fun setupEvents()
    abstract fun setValues()
}