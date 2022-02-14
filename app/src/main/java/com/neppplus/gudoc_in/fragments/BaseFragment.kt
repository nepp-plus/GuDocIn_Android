package com.neppplus.gudoc_in.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.neppplus.gudoc_in.api.ServerAPI
import com.neppplus.gudoc_in.api.ServerAPIInterface

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