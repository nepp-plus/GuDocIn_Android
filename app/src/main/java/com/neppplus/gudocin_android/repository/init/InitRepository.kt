package com.neppplus.gudocin_android.repository.init

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.GlobalData
import com.neppplus.gudocin_android.network.RetrofitServiceInstance
import com.neppplus.gudocin_android.util.context.ContextUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class InitRepository @Inject constructor(
  @ApplicationContext private val context: Context,
  private val retrofitServiceInstance: RetrofitServiceInstance
) {
  fun getRequestInfo(liveDataList: MutableLiveData<BasicResponse>) {
    val call: Call<BasicResponse> = retrofitServiceInstance.getRequestInfo()
    call.enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        liveDataList.postValue(response.body())
        GlobalData.loginUser = response.body()!!.data.user
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
        liveDataList.postValue(null)
      }
    })
  }

  fun patchRequestUpdateUserInfo(liveDataList: MutableLiveData<BasicResponse>) {
    val call: Call<BasicResponse> =
      retrofitServiceInstance.patchRequestUpdateUserInfo("android_device_token", ContextUtil.getDeviceToken(context))
    call.enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        liveDataList.postValue(response.body())
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
        liveDataList.postValue(null)
      }
    })
  }

}